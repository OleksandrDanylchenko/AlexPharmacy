package com.alexd.AlexPharmacy.resources;

import com.alexd.AlexPharmacy.domain.Manufacturer;
import com.alexd.AlexPharmacy.domain.PharmacyDomain;
import com.alexd.AlexPharmacy.repository.ManufacturerRepository;
import com.alexd.AlexPharmacy.service.DataService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/manufacturers")
public class ManufacturerResource {

    /**
     * Manufacturers table repository.
     */
    private final ManufacturerRepository manufacturerRepository;

    /**
     * DB interaction service.
     */
    private final DataService dataService;

    /**
     * Specified service for complex request.
     */
    private final ManufacturerService manufacturerService;

    /**
     * Spring DI constructor for ManufacturerRepository and DataService.
     *
     * @param manufacturerRepository Manufacturers table repository
     * @param dataService            DB interaction service
     * @param manufacturerService    Specified service for complex request
     */
    public ManufacturerResource(final ManufacturerRepository manufacturerRepository, final DataService dataService,
                                final ManufacturerService manufacturerService) {
        this.manufacturerRepository = manufacturerRepository;
        this.dataService = dataService;
        this.manufacturerService = manufacturerService;
    }

    /**
     * Manufacturers table getter.
     *
     * @return List of Manufacturer objects
     */
    @GetMapping
    public ResponseEntity<List<? extends PharmacyDomain>> getManufacturers() {
        var manufacturers = dataService.getAllRecords(manufacturerRepository);
        return new ResponseEntity<>(manufacturers, HttpStatus.OK);
    }

    /**
     * Manufacturer record getter.
     *
     * @param id ID of desired Manufacturer record
     * @return Basket record founded by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<? extends PharmacyDomain> getManufacturerById(@PathVariable final String id) {
        var foundManufacturer = dataService.getRecordById(manufacturerRepository, id);
        return new ResponseEntity<>(foundManufacturer, HttpStatus.OK);
    }

    /**
     * Specialized request endpoint method:
     * Знайти торгові марки постачальників, що постачають препарат drugName
     *
     * @param drugName Name of drug, which supplied by manufacturer
     * @return List of manufacturers, which supply drug with drugName
     */
    @GetMapping("/trademarksSupplyDrug")
    public ResponseEntity<List<String>> get(@RequestParam final String drugName) {
        var foundTrademarks = manufacturerService.getTrademarksBySupplyDrug(drugName);
        return new ResponseEntity<>(foundTrademarks, HttpStatus.OK);
    }

    /**
     * Manufacturer record adding method.
     *
     * @param newManufacturer New given manufacturer
     * @return Status of adding operation
     */
    @PostMapping
    public ResponseEntity<Object> addManufacturer(@Valid @RequestBody final Manufacturer newManufacturer) {
        dataService.saveRecord(manufacturerRepository, newManufacturer);
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
        dataService.updateRecord(manufacturerRepository, updManufacturer);
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
        dataService.deleteRecord(manufacturerRepository, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
