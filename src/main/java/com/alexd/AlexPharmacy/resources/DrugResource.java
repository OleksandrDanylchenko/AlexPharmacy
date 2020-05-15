package com.alexd.AlexPharmacy.resources;

import com.alexd.AlexPharmacy.domain.Drug;
import com.alexd.AlexPharmacy.domain.PharmacyDomain;
import com.alexd.AlexPharmacy.repository.DrugRepository;
import com.alexd.AlexPharmacy.service.DataService;
import com.alexd.AlexPharmacy.service.DrugService;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RestController
@RequestMapping("/drugs")
public class DrugResource {

    /**
     * Drugs table repository.
     */
    private final DrugRepository drugRepository;

    /**
     * DB interaction service.
     */
    private final DataService dataService;

    /**
     * Specified service for complex request with drug repository.
     */
    private final DrugService drugService;

    /**
     * Spring DI constructor for DrugRepository and DataService.
     *
     * @param drugRepository Drugs table repository
     * @param dataService    DB interaction service
     * @param drugService    Specified service for complex request with drug repository
     */
    public DrugResource(final DrugRepository drugRepository, final DataService dataService, DrugService drugService) {
        this.drugRepository = drugRepository;
        this.dataService = dataService;
        this.drugService = drugService;
    }

    /**
     * Drugs table getter.
     *
     * @return List of Drug objects
     */
    @GetMapping
    public ResponseEntity<List<? extends PharmacyDomain>> getDrugs() {
        var drugs = dataService.getAllRecords(drugRepository);
        return new ResponseEntity<>(drugs, HttpStatus.OK);
    }

    /**
     * Drug record getter.
     *
     * @param id ID of desired Drug record
     * @return Drug record founded by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<? extends PharmacyDomain> getDrugById(@PathVariable final String id) {
        var foundDrug = dataService.getRecordById(drugRepository, id);
        return new ResponseEntity<>(foundDrug, HttpStatus.OK);
    }

    /**
     * Specialized request endpoint method:
     * Знайти назви препаратів, придбаних клієнтом з ім'ям clientFirstName та датою народження clientBirthday.
     *
     * @param firstName First name of specified client
     * @param birthday  Birthday of specified client
     * @return List of drug names, which was bought by specified client
     */
    @GetMapping("/boughtDrugByClient")
    public ResponseEntity<List<String>> getBoughtDrugs(@RequestParam final String firstName,
                                                       @RequestParam
                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                       final LocalDate birthday) {
        var foundNames = drugService.getBoughtDrugNamesByClient(firstName, birthday);
        return new ResponseEntity<>(foundNames, HttpStatus.OK);
    }

    /**
     * Drug record adding method.
     *
     * @param newDrug New given drug
     * @return Status of adding operation
     */
    @PostMapping
    public ResponseEntity<Object> addDrug(@Valid @RequestBody final Drug newDrug) {
        dataService.saveRecord(drugRepository, newDrug);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Drug record updating method.
     *
     * @param updDrug Updating given drug
     * @return Status of updating operation
     */
    @PutMapping
    public ResponseEntity<Object> updateDrug(@Valid @RequestBody final Drug updDrug) {
        dataService.updateRecord(drugRepository, updDrug);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Drug record deleting method.
     *
     * @param id ID of deleting Drug record
     * @return Status of deleting operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDrug(@PathVariable final String id) {
        dataService.deleteRecord(drugRepository, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
