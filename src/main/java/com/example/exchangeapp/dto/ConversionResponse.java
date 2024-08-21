package com.example.exchangeapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConversionResponse {

    private String transactionId;
    private double convertedAmount;
}
