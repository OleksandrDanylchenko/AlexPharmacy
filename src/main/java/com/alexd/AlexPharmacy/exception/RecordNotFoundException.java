package com.alexd.AlexPharmacy.exception;

public class RecordNotFoundException extends RuntimeException {

    /**
     * Constructor for adding custom message for RecordNotFoundException.
     *
     * @param recordId ID of not founded record
     */
    public RecordNotFoundException(final String recordId) {
        super(recordId);
    }

}
