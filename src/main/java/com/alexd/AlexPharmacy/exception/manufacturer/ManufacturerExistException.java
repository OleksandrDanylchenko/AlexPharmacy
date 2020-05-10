package com.alexd.AlexPharmacy.exception.manufacturer;

public class ManufacturerExistException extends RuntimeException {

    /**
     * Constructor for adding custom message for ManufacturerExistException.
     *
     * @param name - name of existing manufacturer
     */
    public ManufacturerExistException(final String name) {
        super(name);
    }

}
