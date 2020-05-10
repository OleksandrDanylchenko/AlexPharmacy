package com.alexd.AlexPharmacy.exception.manufacturer;

public class ManufacturerNotFoundException extends RuntimeException {

    /**
     * Constructor for adding custom message for ManufacturerNotFoundException.
     *
     * @param basketId - ID of not founded manufacturer
     */
    public ManufacturerNotFoundException(final String basketId) {
        super(basketId);
    }

}
