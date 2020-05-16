package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RepositoryRestResource
public interface DrugRepository extends JpaRepository<Drug, Long> {

    /**
     * Знайти назви препаратів, придбаних клієнтом з ім'ям clientFirstName та датою народження clientBirthday.
     *
     * @param firstName First name of specified client
     * @param birthday  Birthday of specified client
     * @return List of drugs, which was bought by specified client
     */
    @Query(value = "SELECT * FROM drugs WHERE drugs.id IN "
            + "( SELECT baskets.drug_id FROM baskets WHERE baskets.client_id IN "
            + "( SELECT clients.id FROM clients WHERE clients.first_name = ?1 AND clients.birthday = ?2 ) )",
            nativeQuery = true)
    List<Drug> findDrugBoughtByClientWithFirstNameAndBirthday(String firstName,
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                      LocalDate birthday);

}
