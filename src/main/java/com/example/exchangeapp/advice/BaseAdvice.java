package com.example.exchangeapp.advice;

import com.example.exchangeapp.constant.ErrorCodes;
import com.example.exchangeapp.dto.Error;
import com.example.exchangeapp.util.ErrorUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseAdvice {

    private final ErrorCodes unknownCode;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Error> handleException(RuntimeException e) {
        log.error("An unknown error occurred", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorUtil.constructError(unknownCode.getCode(), e.getMessage()));
    }
}
