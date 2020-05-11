package com.alexd.AlexPharmacy.resources;

import com.alexd.AlexPharmacy.domain.Basket;
import com.alexd.AlexPharmacy.domain.PharmacyDomain;
import com.alexd.AlexPharmacy.repository.BasketRepository;
import com.alexd.AlexPharmacy.service.DataService;
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
     * Baskets table repository.
     */
    private final BasketRepository basketRepository;

    /**
     * DB interaction service.
     */
    private final DataService dataService;

    /**
     * /**
     * Spring DI constructor for BasketRepository and DataService.
     *
     * @param basketRepository Baskets table repository.
     * @param dataService      DB interaction service
     */
    public BasketResource(final BasketRepository basketRepository, final DataService dataService) {
        this.basketRepository = basketRepository;
        this.dataService = dataService;
    }

    /**
     * Baskets table getter.
     *
     * @return List of Basket objects
     */
    @GetMapping
    public ResponseEntity<List<? extends PharmacyDomain>> getBaskets() {
        var baskets = dataService.getAllRecords(basketRepository);
        return new ResponseEntity<>(baskets, HttpStatus.OK);
    }

    /**
     * Basket record getter.
     *
     * @param id ID of desired Basket record
     * @return Basket record founded by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<? extends PharmacyDomain> getBasketById(@PathVariable final String id) {
        var foundBasket = dataService.getRecordById(basketRepository, id);
        return new ResponseEntity<>(foundBasket, HttpStatus.OK);
    }

    /**
     * Basket record adding method.
     *
     * @param newBasket New given basket
     * @return Status of adding operation
     */
    @PostMapping
    public ResponseEntity<Object> addBasket(@Valid @RequestBody final Basket newBasket) {
        dataService.saveRecord(basketRepository, newBasket);
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
        dataService.updateRecord(basketRepository, updBasket);
        return new ResponseEntity<>(updBasket, HttpStatus.OK);
    }

    /**
     * Basket record deleting method.
     *
     * @param id ID of deleting Basket record
     * @return Status of deleting operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBasket(@PathVariable final String id) {
        dataService.deleteRecord(basketRepository, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
