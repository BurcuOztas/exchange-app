package com.example.exchangeapp.dto;

import java.io.Serializable;

public enum CurrenciesEnum implements Serializable {
    USD,
    EUR,
    GBP,
    JPY,
    AUD,
    CAD,
    CHF,
    CNY,
    INR,
    TRY,
    RUB;

    @Override
    public String toString() {
        return name();
    }
}