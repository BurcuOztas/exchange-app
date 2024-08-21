package com.example.exchangeapp.converter;

import com.example.exchangeapp.dto.ConversionResponse;
import com.example.exchangeapp.entity.Conversion;

import java.time.LocalDateTime;
import java.util.UUID;

public final class ConversionConverter {

    private ConversionConverter() {
        throw new IllegalStateException("Converter Class");
    }

    public static ConversionResponse convertToResponse(Conversion conversion) {
        return new ConversionResponse(
                conversion.getTransactionId(),
                conversion.getConvertedAmount()
        );
    }

    public static Conversion convertToEntity(String fromCurrency, String toCurrency, double amount, double convertedAmount) {
        return Conversion.builder()
                .transactionId(UUID.randomUUID().toString())
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .amount(amount)
                .convertedAmount(convertedAmount)
                .transactionDate(LocalDateTime.now())
                .build();
    }
}
