package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
}
