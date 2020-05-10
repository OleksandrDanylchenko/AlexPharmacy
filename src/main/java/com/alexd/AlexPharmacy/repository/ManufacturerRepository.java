package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository
        extends JpaRepository<Manufacturer, Long> {
}
