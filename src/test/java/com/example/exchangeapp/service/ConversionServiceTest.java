package com.example.exchangeapp.service;

import com.example.exchangeapp.dto.ConversionRequest;
import com.example.exchangeapp.dto.ConversionResponse;
import com.example.exchangeapp.dto.ExchangeRateResponse;
import com.example.exchangeapp.entity.Conversion;
import com.example.exchangeapp.exception.RateNotFoundException;
import com.example.exchangeapp.repository.ConversionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionServiceTest {

    @Mock
    private ConversionRepository conversionRepository;

    @Mock
    private ExchangeService exchangeService;

    @InjectMocks
    private ConversionService conversionService;

    @Test
    void shouldConvertCurrency_whenExchangeRateIsAvailable() {
        ConversionRequest request = new ConversionRequest(100.0, "USD", "EUR");
        double mockRate = 0.85;
        double expectedConvertedAmount = request.getAmount() * mockRate;

        ExchangeRateResponse mockResponse = ExchangeRateResponse.builder()
                .success(true)
                .timestamp(12345L)
                .base("USD")
                .rates(Collections.singletonMap("EUR", mockRate))
                .build();

        when(exchangeService.getExchangeRate(anyString(), anyString()))
                .thenReturn(mockResponse);

        Conversion mockConversion = Conversion.builder()
                .transactionId(UUID.randomUUID().toString())
                .fromCurrency("USD")
                .toCurrency("EUR")
                .amount(request.getAmount())
                .convertedAmount(expectedConvertedAmount)
                .transactionDate(LocalDateTime.now())
                .build();

        when(conversionRepository.save(any(Conversion.class))).thenReturn(mockConversion);

        ConversionResponse response = conversionService.convertCurrency(request);

        assertNotNull(response);
        assertEquals(expectedConvertedAmount, response.getConvertedAmount(), 0.001);
        verify(conversionRepository, times(1)).save(any(Conversion.class));
    }

    @Test
    void shouldThrowRateNotFoundException_whenExchangeRateIsNotAvailable() {
        ConversionRequest request = new ConversionRequest(100.0, "USD", "EUR");

        when(exchangeService.getExchangeRate(anyString(), anyString())).thenReturn(null);

        assertThrows(RateNotFoundException.class, () -> conversionService.convertCurrency(request));
        verify(conversionRepository, never()).save(any(Conversion.class));
    }

    @Test
    void shouldReturnConversionHistory_whenHistoryIsAvailable() {
        Pageable pageable = PageRequest.of(0, 10);
        Conversion mockConversion = Conversion.builder()
                .transactionId(UUID.randomUUID().toString())
                .fromCurrency("USD")
                .toCurrency("EUR")
                .amount(100.0)
                .convertedAmount(85.0)
                .transactionDate(LocalDateTime.now())
                .build();

        Page<Conversion> conversionPage = new PageImpl<>(Collections.singletonList(mockConversion), pageable, 1);

        when(conversionRepository.findAll(any(Pageable.class))).thenReturn(conversionPage);

        Page<ConversionResponse> responsePage = conversionService.getAllHistory(pageable);

        assertNotNull(responsePage);
        assertEquals(1, responsePage.getTotalElements());
        verify(conversionRepository, times(1)).findAll(any(Pageable.class));
    }
}