package com.alexd.AlexPharmacy.exception;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

public class ApiError {

    /**
     * HTTP status code.
     */
    private final HttpStatus status;

    /**
     * Error message associated with exception.
     */
    private final String message;

    /**
     * List of constructed error messages.
     */
    private final List<String> errors;

    /**
     * Multiple errors constructor.
     *
     * @param status  - HTTP status code
     * @param message - Error message associated with exception
     * @param errors  - List of constructed error messages
     */
    public ApiError(final HttpStatus status, final String message,
                    final List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    /**
     * One error constructor.
     *
     * @param status  - HTTP status code
     * @param message - Error message associated with exception
     * @param error   - List of constructed error messages
     */
    public ApiError(final HttpStatus status, final String message,
                    final String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    /**
     * @return - returns HTTP status code
     */
    public HttpStatus getStatus() {
        return status;
    }

    /**
     * @return - returns error message associated with exception
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return - returns list of constructed error messages
     */
    public List<String> getErrors() {
        return errors;
    }
}
