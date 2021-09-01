package com.example.flashcardsapi.exception;

import org.springframework.http.HttpStatus;

public class FlashcardsApiException extends RuntimeException{

    private final Integer status;
    private final String message;
    private final String error;

    public FlashcardsApiException(HttpStatus httpStatus, String message) {
        super();
        this.status = httpStatus.value();
        this.error = httpStatus.name();
        this.message = message;
    }

    public FlashcardsApiException(HttpStatus httpStatus, String message, Throwable cause) {
        super(cause);
        this.status = httpStatus.value();
        this.error = httpStatus.name();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() { return error; }

}
