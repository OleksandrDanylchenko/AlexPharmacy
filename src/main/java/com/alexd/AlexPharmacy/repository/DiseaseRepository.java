package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RepositoryRestResource
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    /**
     * Знайти назви хвороб, які лікуються препаратами виробника з торговою маркою manufacturerTrademark.
     *
     * @param manufacturerTrademark Name of drug, which supplied by manufacturer
     * @return List of manufacturers,trade who supply drug with drugName
     */
    @Query(value = "SELECT * FROM diseases WHERE diseases.id IN "
            + "( SELECT disease_drug.disease_id FROM disease_drug WHERE disease_drug.drug_id IN "
            + "( SELECT drugs.id FROM drugs WHERE drugs.manufacturer_id IN "
            + "( SELECT manufacturers.id FROM manufacturers WHERE manufacturers.trademark = ?1 ) ) )",
            nativeQuery = true)
    List<Disease> findDiseasesByDrugsFromManufacturer(String manufacturerTrademark);

}
