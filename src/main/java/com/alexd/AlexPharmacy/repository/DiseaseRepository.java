package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
}
