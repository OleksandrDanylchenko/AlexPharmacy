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

    /**
     * Знайти прізвища та дні народження клієнтів, що придбали хоча б один препарат виробника manufacturerName.
     *
     * @param manufacturerName Name of manufacturer, who provide drug
     * @return List of clients, who bought at least one drug from manufacturer
     */
    @Query(value = "SELECT * FROM clients WHERE clients.id IN "
            + "( SELECT baskets.client_id FROM baskets WHERE baskets.drug_id IN "
            + "( SELECT drugs.id FROM drugs WHERE drugs.manufacturer_id IN "
            + "( SELECT manufacturer_id FROM manufacturers WHERE manufacturers.trademark = ?1 ) ) )",
            nativeQuery = true)
    List<Client> findLastNameAndBirthdayClientWhoBoughtDrugByManufacturer(String manufacturerName);

}
