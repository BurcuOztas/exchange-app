package com.example.exchangeapp.controller;

import com.example.exchangeapp.dto.CurrenciesEnum;
import com.example.exchangeapp.dto.ExchangeRateResponse;
import com.example.exchangeapp.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/rate")
    public ExchangeRateResponse getExchangeRate(@RequestParam(value = "fromCurrency", defaultValue = "EUR") CurrenciesEnum fromCurrency,
                                                @RequestParam(value = "toCurrency", defaultValue = "USD") CurrenciesEnum toCurrency) {
        return exchangeService.getExchangeRate(fromCurrency.toString(), toCurrency.toString());
    }
}