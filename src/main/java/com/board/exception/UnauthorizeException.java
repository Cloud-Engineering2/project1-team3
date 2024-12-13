package com.board.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UnauthorizeException extends RuntimeException {
    private final String message;

    public UnauthorizeException(String message) {
        super(message);
        this.message = message;
    }
}
