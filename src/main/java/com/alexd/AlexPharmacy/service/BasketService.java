package com.alexd.AlexPharmacy.service;

import com.alexd.AlexPharmacy.domain.Basket;
import com.alexd.AlexPharmacy.exception.RecordNotFoundException;
import com.alexd.AlexPharmacy.repository.BasketRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketService {

    /**
     * Spring Data repository for baskets table.
     */
    private final BasketRepository basketRepository;

    /**
     * Spring DI constructor with BasketRepository.
     *
     * @param basketRepository Spring Data repository for baskets table
     */
    public BasketService(final BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    /**
     * Service method, which returns all presented baskets.
     *
     * @return List of baskets presented in table
     */
    public List<Basket> getAllBaskets() {
        return basketRepository.findAll();
    }

    /**
     * Service method, which finds Basket specified by id basket record.
     *
     * @param id ID of desired record
     * @return Basket founded by ID
     */
    public Basket getBasketById(final String id) {
        try {
            var longId = NumberUtils.createLong(id);
            var foundedBasket = basketRepository.findById(longId);
            return foundedBasket.orElseThrow(IllegalArgumentException::new);
        } catch (IllegalArgumentException ex) {
            throw new RecordNotFoundException(id);
        }
    }

    /**
     * Service method, which add to DB given basket record.
     *
     * @param newBasket New given basket
     */
    public void addNewBasket(final Basket newBasket) {
        basketRepository.save(newBasket);
    }

    /**
     * Service method, which updates given basket record.
     *
     * @param updBasket Updating given basket
     */
    public void updateBasket(final @NotNull Basket updBasket) {
        var basketFromDB = getBasketById(String.valueOf(updBasket.getId()));
        BeanUtils.copyProperties(updBasket, basketFromDB, "id");
        basketRepository.save(basketFromDB);
    }

    /**
     * Service method, which deletes Basket record by ID.
     *
     * @param id Deleting basket ID
     */
    public void deleteBasket(final String id) {
        var deletionDiscount = getBasketById(id);
        basketRepository.delete(deletionDiscount);
    }

}
