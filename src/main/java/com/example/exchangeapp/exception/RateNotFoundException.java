package com.example.exchangeapp.exception;

import com.example.exchangeapp.constant.ErrorCodes;

public class RateNotFoundException extends CommonException {
    public RateNotFoundException() {
        super("Rate not found!", ErrorCodes.RATE_NOT_FOUND);
    }
}