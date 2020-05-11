package com.alexd.AlexPharmacy.resources;

import com.alexd.AlexPharmacy.domain.Disease;
import com.alexd.AlexPharmacy.domain.PharmacyDomain;
import com.alexd.AlexPharmacy.repository.DiseaseRepository;
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
@RequestMapping("/diseases")
public class DiseaseResource {

    /**
     * Diseases table repository.
     */
    private final DiseaseRepository diseaseRepository;

    /**
     * DB interaction service.
     */
    private final DataService dataService;

    /**
     * Spring DI constructor for ManufacturerRepository and DataService.
     *
     * @param diseaseRepository Diseases table repository
     * @param dataService       DB interaction service
     */
    public DiseaseResource(final DiseaseRepository diseaseRepository, final DataService dataService) {
        this.diseaseRepository = diseaseRepository;
        this.dataService = dataService;
    }

    /**
     * Diseases table getter.
     *
     * @return List of Diseases objects
     */
    @GetMapping
    public ResponseEntity<List<? extends PharmacyDomain>> getDiseases() {
        var diseases = dataService.getAllRecords(diseaseRepository);
        return new ResponseEntity<>(diseases, HttpStatus.OK);
    }

    /**
     * Disease record getter.
     *
     * @param id ID of desired Disease record
     * @return Disease record founded by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<? extends PharmacyDomain> getDiseaseById(@PathVariable final String id) {
        var foundDisease = dataService.getRecordById(diseaseRepository, id);
        return new ResponseEntity<>(foundDisease, HttpStatus.OK);
    }

    /**
     * Disease record adding method.
     *
     * @param newDisease New given disease
     * @return Status of adding operation
     */
    @PostMapping
    public ResponseEntity<Object> addDisease(@Valid @RequestBody final Disease newDisease) {
        dataService.saveRecord(diseaseRepository, newDisease);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Disease record updating method.
     *
     * @param updDisease Updating given disease
     * @return Status of updating operation
     */
    @PutMapping
    public ResponseEntity<Object> updateDisease(@Valid @RequestBody final Disease updDisease) {
        dataService.updateRecord(diseaseRepository, updDisease);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Disease record deleting method.
     *
     * @param id ID of deleting Disease record
     * @return Status of deleting operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDisease(@PathVariable final String id) {
        dataService.deleteRecord(diseaseRepository, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
