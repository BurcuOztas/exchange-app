package com.example.exchangeapp.client;

import com.example.exchangeapp.dto.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "exchangeRateClient", url = "${external.api.url}")
public interface ExchangeRateClient {

    @GetMapping("/latest?access_key=${external.api.key}")
    ExchangeRateResponse getExchangeRate(
            @RequestParam("base") String baseCurrency,
            @RequestParam("symbols") String targetCurrency
    );
}
