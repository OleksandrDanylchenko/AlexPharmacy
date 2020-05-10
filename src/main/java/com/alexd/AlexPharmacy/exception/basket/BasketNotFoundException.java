package com.alexd.AlexPharmacy.exception.basket;

public class BasketNotFoundException extends RuntimeException {

    /**
     *  Constructor for adding custom message for BasketNotFoundException.
     *
     * @param basketId - ID of not founded basket
     */
    public BasketNotFoundException(final String basketId) {
        super(basketId);
    }

}
