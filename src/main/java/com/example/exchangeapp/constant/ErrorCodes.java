package com.example.exchangeapp.constant;

public enum ErrorCodes {

    EXCHANGE_NOT_FOUND(1000, "Exchange not found"),
    RATE_NOT_FOUND(1001, "Rate not found"),
    BASE_CURRENCY_ACCESS_RESTRICTED(1002, "Base currency access restricted"),
    INVALID_DATE_FORMAT(1003, "Invalid Date Format"),
    UNKNOWN(1099, "Unknown error occurred");

    private final int code;
    private final String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("%d: %s", code, message);
    }
}