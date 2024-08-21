package com.example.exchangeapp.exception;

import com.example.exchangeapp.constant.ErrorCodes;

public class ExchangeNotFoundException extends CommonException {
    public ExchangeNotFoundException() {
        super("Exchange not found.", ErrorCodes.EXCHANGE_NOT_FOUND);
    }
}