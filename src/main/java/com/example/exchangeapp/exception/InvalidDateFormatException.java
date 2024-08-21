package com.example.exchangeapp.exception;

import com.example.exchangeapp.constant.ErrorCodes;

public class InvalidDateFormatException extends CommonException {
    public InvalidDateFormatException() {
        super("Invalid date format!", ErrorCodes.INVALID_DATE_FORMAT);
    }
}