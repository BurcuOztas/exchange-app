package com.example.exchangeapp.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
public class ExchangeRateResponse {
    private boolean success;
    private long timestamp;
    private String base;
    private LocalDate date;
    private Map<String, Double> rates;
}
