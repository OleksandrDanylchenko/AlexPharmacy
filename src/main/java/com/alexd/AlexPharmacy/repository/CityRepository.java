package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CityRepository extends JpaRepository<City, Long> {
}
