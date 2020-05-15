package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    @Query(value = "SELECT DISTINCT manufacturers.trademark FROM manufacturers, drugs "
            + "WHERE manufacturers.id = drugs.manufacturer_id and drugs.name = ?1",
            nativeQuery = true)
    List<String> findManufacturersTrademarksWhoSupplyDrug(String drugName);
}
