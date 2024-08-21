package com.example.exchangeapp.util;

import com.example.exchangeapp.dto.Error;
import lombok.experimental.UtilityClass;

import java.util.function.BiFunction;

@UtilityClass
public class ErrorUtil {

    private static final BiFunction<Integer, String, Error> errorConstructor = Error::new;

    public Error constructError(int code, String message) {
        return errorConstructor.apply(code, message);
    }
}
