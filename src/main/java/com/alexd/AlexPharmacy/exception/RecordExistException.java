package com.alexd.AlexPharmacy.exception;

public class RecordExistException extends RuntimeException {

    /**
     * Constructor for adding custom message for RecordExistException.
     *
     * @param message Custom exist message
     */
    public RecordExistException(final String message) {
        super(message);
    }

}
