package com.example.exchangeapp.advice;

import com.example.exchangeapp.constant.ErrorCodes;
import com.example.exchangeapp.dto.Error;
import com.example.exchangeapp.exception.BaseCurrencyAccessRestrictedException;
import com.example.exchangeapp.exception.CommonException;
import com.example.exchangeapp.exception.ExchangeNotFoundException;
import com.example.exchangeapp.exception.InvalidDateFormatException;
import com.example.exchangeapp.util.ErrorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class ServiceAdvice extends BaseAdvice {

    public ServiceAdvice() {
        super(ErrorCodes.UNKNOWN);
    }

    @ExceptionHandler(ExchangeNotFoundException.class)
    public ResponseEntity<Error> handleExchangeNotFound(ExchangeNotFoundException e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorUtil.constructError(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Error> handleCommonException(CommonException e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorUtil.constructError(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(BaseCurrencyAccessRestrictedException.class)
    public ResponseEntity<Error> handleCommonException(BaseCurrencyAccessRestrictedException e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorUtil.constructError(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    public ResponseEntity<Error> handleCommonException(InvalidDateFormatException e) {
        log.info(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorUtil.constructError(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        if (Objects.requireNonNull(ex.getRequiredType()).isAssignableFrom(LocalDate.class)) {
            return new ResponseEntity<>("Invalid date format. Please use the format 'yyyy-MM-dd'.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Invalid argument type.", HttpStatus.BAD_REQUEST);
    }
}
