package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@SuppressWarnings("unused")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = "SELECT clients.last_name + ' ' + CONVERT(VARCHAR(15), clients.birthday, 4)"
            + "FROM clients WHERE clients.id IN "
            + "( SELECT baskets.client_id FROM baskets WHERE baskets.drug_id IN "
            + "( SELECT drugs.id FROM drugs WHERE drugs.manufacturer_id IN "
            + "( SELECT manufacturer_id FROM manufacturers WHERE manufacturers.trademark = ?1 ) ) )",
            nativeQuery = true)
    List<String> findLastNameAndBirthdayClientWhoBoughtDrugByManufacturer(String manufacturerName);
}
