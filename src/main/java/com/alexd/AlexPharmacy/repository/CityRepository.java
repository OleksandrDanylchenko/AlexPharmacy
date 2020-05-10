package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200",
        "http://localhost:8081"})
@RepositoryRestResource
public interface CityRepository extends JpaRepository<City, Long> {
}
