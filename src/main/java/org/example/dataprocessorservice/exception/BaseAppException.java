package org.example.dataprocessorservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseAppException extends RuntimeException {
    private final String code;
    private final HttpStatus statusCode;

    public BaseAppException(ExceptionName exceptionName) {
        super(exceptionName.getMessage());
        this.code = exceptionName.getCode();
        this.statusCode = exceptionName.getHttpStatus();
    }
}
