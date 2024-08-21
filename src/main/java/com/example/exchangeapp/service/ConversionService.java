package com.example.exchangeapp.service;

import com.example.exchangeapp.converter.ConversionConverter;
import com.example.exchangeapp.dto.ConversionRequest;
import com.example.exchangeapp.dto.ConversionResponse;
import com.example.exchangeapp.entity.Conversion;
import com.example.exchangeapp.exception.RateNotFoundException;
import com.example.exchangeapp.repository.ConversionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversionService {

    private final ConversionRepository conversionRepository;
    private final ExchangeService exchangeService;

    public ConversionResponse convertCurrency(ConversionRequest request) {
        double convertedAmount = calculateConversion(request);
        Conversion conversion = ConversionConverter.convertToEntity(
                request.getFromCurrency(),
                request.getToCurrency(),
                request.getAmount(),
                convertedAmount
        );
        Conversion savedConversion = conversionRepository.save(conversion);
        return ConversionConverter.convertToResponse(savedConversion);
    }

    public Page<ConversionResponse> getAllHistory(Pageable pageable) {
        Pageable defaultPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("transactionDate").descending());
        Page<Conversion> conversionPage = conversionRepository.findAll(defaultPageable);
        return conversionPage.map(ConversionConverter::convertToResponse);
    }

    private double calculateConversion(ConversionRequest request) {
        return Optional.ofNullable(exchangeService.getExchangeRate(request.getFromCurrency(), request.getToCurrency()))
                .map(rate -> rate.getRates().get(request.getToCurrency()) * request.getAmount())
                .orElseThrow(RateNotFoundException::new);
    }
}
