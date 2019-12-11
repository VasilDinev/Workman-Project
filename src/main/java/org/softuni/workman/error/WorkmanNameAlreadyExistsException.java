package org.softuni.workman.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Product name exists.")
public class WorkmanNameAlreadyExistsException extends RuntimeException {

    private int statusCode;

    public WorkmanNameAlreadyExistsException() {
        this.statusCode = 409;
    }

    public WorkmanNameAlreadyExistsException(String message) {
        super(message);
        this.statusCode = 409;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
