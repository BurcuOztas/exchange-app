package com.example.exchangeapp.exception;

import com.example.exchangeapp.constant.ErrorCodes;

public class CommonException extends RuntimeException {
    private final ErrorCodes errorCode;

    public CommonException(ErrorCodes errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CommonException(String message, ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return String.format("%s (Error Code: %d)", super.getMessage(), errorCode.getCode());
    }
}
