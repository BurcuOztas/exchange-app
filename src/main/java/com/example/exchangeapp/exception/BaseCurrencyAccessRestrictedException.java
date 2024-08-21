package com.example.exchangeapp.exception;

import com.example.exchangeapp.constant.ErrorCodes;

public class BaseCurrencyAccessRestrictedException extends CommonException {
    public BaseCurrencyAccessRestrictedException() {
        super("Base currency access restricted exception", ErrorCodes.BASE_CURRENCY_ACCESS_RESTRICTED);
    }
}