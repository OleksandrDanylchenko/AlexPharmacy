package com.alexd.AlexPharmacy.resources;

import com.alexd.AlexPharmacy.domain.Basket;
import com.alexd.AlexPharmacy.service.BasketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/baskets")
public class BasketResource {

    /**
     * Baskets table interaction service.
     */
    private final BasketService basketService;

    /**
     * Spring DI constructor.
     *
     * @param basketService - service for interaction with baskets table
     */
    public BasketResource(final BasketService basketService) {
        this.basketService = basketService;
    }

    /**
     * Table getter.
     *
     * @return List of basket objects
     */
    @GetMapping
    public ResponseEntity<List<Basket>> getBaskets() {
        var baskets = basketService.getAllBaskets();
        return new ResponseEntity<>(baskets, HttpStatus.OK);
    }

    /**
     * Basket record getter.
     *
     * @param id Id of desired record
     * @return Record founded by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Basket> getBasketById(@PathVariable final String id) {
        var foundedBasket = basketService.getBasketById(id);
        return new ResponseEntity<>(foundedBasket, HttpStatus.OK);
    }

    /**
     * Basket record adding method.
     *
     * @param newBasket New given basket
     * @return Status of adding operation
     */
    @PostMapping
    public ResponseEntity<Object> addBasket(@Valid @RequestBody final Basket newBasket) {
        basketService.addNewBasket(newBasket);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Basket record updating method.
     *
     * @param updBasket Updating given basket
     * @return Status of updating operation
     */
    @PutMapping
    public ResponseEntity<Object> updateBasket(@Valid @RequestBody final Basket updBasket) {
        basketService.updateBasket(updBasket);
        return new ResponseEntity<>(updBasket, HttpStatus.OK);
    }

    /**
     * Basket record deleting method.
     *
     * @param id Id of deleting record
     * @return Status of deleting operation
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBasket(@PathVariable final String id) {
        basketService.deleteBasket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
