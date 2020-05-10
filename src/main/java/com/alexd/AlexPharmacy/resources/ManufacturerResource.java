package com.alexd.AlexPharmacy.resources;

import com.alexd.AlexPharmacy.domain.Manufacturer;
import com.alexd.AlexPharmacy.service.ManufacturerService;
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
@RequestMapping("/manufacturers")
public class ManufacturerResource {

    /**
     * Manufacturer table interaction service.
     */
    private final ManufacturerService manufacturerService;

    /**
     * Spring DI constructor for ManufacturerService.
     *
     * @param manufacturerService - service for interaction with manufacturers table
     */
    public ManufacturerResource(final ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    /**
     * Manufacturers table getter.
     *
     * @return List of Manufacturer objects
     */
    @GetMapping
    public ResponseEntity<List<Manufacturer>> getManufacturers() {
        var manufacturers = manufacturerService.getAllManufacturers();
        return new ResponseEntity<>(manufacturers, HttpStatus.OK);
    }

    /**
     * Manufacturer record getter.
     *
     * @param id ID of desired Manufacturer record
     * @return Basket record founded by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable final String id) {
        var foundedManufacturer = manufacturerService.getManufacturerById(id);
        return new ResponseEntity<>(foundedManufacturer, HttpStatus.OK);
    }

    /**
     * Manufacturer record adding method.
     *
     * @param newManufacturer New given manufacturer
     * @return Status of adding operation
     */
    @PostMapping
    public ResponseEntity<Object> addManufacturer(@Valid @RequestBody final Manufacturer newManufacturer) {
        manufacturerService.addNewManufacturer(newManufacturer);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Manufacturer record updating method.
     *
     * @param updManufacturer Updating given manufacturer
     * @return Status of updating operation
     */
    @PutMapping
    public ResponseEntity<Object> updateManufacturer(@Valid @RequestBody final Manufacturer updManufacturer) {
        manufacturerService.updateManufacturer(updManufacturer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Manufacturer record deleting method.
     *
     * @param id ID of deleting Manufacturer record
     * @return Status of deleting operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteManufacturer(@PathVariable final String id) {
        manufacturerService.deleteManufacturer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
