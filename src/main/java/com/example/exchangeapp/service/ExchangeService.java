package com.example.exchangeapp.service;

import com.example.exchangeapp.client.ExchangeRateClient;
import com.example.exchangeapp.dto.ExchangeRateResponse;
import com.example.exchangeapp.exception.BaseCurrencyAccessRestrictedException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeRateClient exchangeRateClient;

    @Cacheable(value = "exchangeRates", key = "#fromCurrency + '-' + #toCurrency")
    public ExchangeRateResponse getExchangeRate(String fromCurrency, String toCurrency) {
        return Optional.ofNullable(exchangeRateClient.getExchangeRate(fromCurrency, toCurrency))
                .filter(ExchangeRateResponse::isSuccess)
                .map(response -> getRateOrThrow(response, toCurrency))
                .orElseThrow(BaseCurrencyAccessRestrictedException::new);
    }

    private ExchangeRateResponse getRateOrThrow(ExchangeRateResponse response, String toCurrency) {
        return Optional.ofNullable(response.getRates().get(toCurrency))
                .map(rate -> response)
                .orElseThrow(BaseCurrencyAccessRestrictedException::new);
    }
}
