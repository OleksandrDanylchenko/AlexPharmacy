package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
}
