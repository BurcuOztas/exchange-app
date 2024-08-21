package com.example.exchangeapp.service;

import com.example.exchangeapp.client.ExchangeRateClient;
import com.example.exchangeapp.dto.ExchangeRateResponse;
import com.example.exchangeapp.exception.BaseCurrencyAccessRestrictedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @InjectMocks
    private ExchangeService exchangeService;

    @Test
    void shouldReturnExchangeRate_whenExchangeRateIsAvailable() {
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        double mockRate = 0.85;

        ExchangeRateResponse mockResponse = ExchangeRateResponse.builder()
                .success(true)
                .timestamp(12345L)
                .base(fromCurrency)
                .rates(Collections.singletonMap(toCurrency, mockRate))
                .build();

        when(exchangeRateClient.getExchangeRate(anyString(), anyString())).thenReturn(mockResponse);

        ExchangeRateResponse response = exchangeService.getExchangeRate(fromCurrency, toCurrency);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals(mockRate, response.getRates().get(toCurrency), 0.001);
        verify(exchangeRateClient, times(1)).getExchangeRate(anyString(), anyString());
    }

    @Test
    void shouldThrowBaseCurrencyAccessRestrictedException_whenRateNotFound() {
        String fromCurrency = "USD";
        String toCurrency = "EUR";

        ExchangeRateResponse mockResponse = ExchangeRateResponse.builder()
                .success(true)
                .timestamp(12345L)
                .base(fromCurrency)
                .rates(Collections.emptyMap())
                .build();

        when(exchangeRateClient.getExchangeRate(anyString(), anyString())).thenReturn(mockResponse);

        assertThrows(BaseCurrencyAccessRestrictedException.class, () -> exchangeService.getExchangeRate(fromCurrency, toCurrency));
        verify(exchangeRateClient, times(1)).getExchangeRate(anyString(), anyString());
    }

    @Test
    void shouldThrowBaseCurrencyAccessRestrictedException_whenExchangeRateIsNotSuccess() {
        String fromCurrency = "USD";
        String toCurrency = "EUR";

        ExchangeRateResponse mockResponse = ExchangeRateResponse.builder()
                .success(false)
                .timestamp(12345L)
                .base(fromCurrency)
                .rates(Collections.singletonMap(toCurrency, 0.85))
                .build();

        when(exchangeRateClient.getExchangeRate(anyString(), anyString())).thenReturn(mockResponse);

        assertThrows(BaseCurrencyAccessRestrictedException.class, () -> exchangeService.getExchangeRate(fromCurrency, toCurrency));
        verify(exchangeRateClient, times(1)).getExchangeRate(anyString(), anyString());
    }
}
