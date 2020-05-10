package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository
        extends JpaRepository<Manufacturer, Long> {
}
