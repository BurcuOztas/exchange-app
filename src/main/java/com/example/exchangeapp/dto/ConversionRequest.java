package com.example.exchangeapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionRequest {

    @NotNull
    @Positive
    private double amount;

    @NotBlank
    private String fromCurrency;

    @NotBlank
    private String toCurrency;
}
