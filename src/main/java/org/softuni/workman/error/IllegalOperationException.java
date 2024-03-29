package org.softuni.workman.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Operation not allowed.")
public class IllegalOperationException extends RuntimeException {

    private int statusCode;

    public IllegalOperationException(String message) {
        super(message);
        this.statusCode = 403;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
