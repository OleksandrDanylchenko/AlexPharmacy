package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200", "http://localhost:8081"})
@RepositoryRestResource
public interface CityRepository extends JpaRepository<City, Long> {

    /**
     * Знайти назви міст, виробники яких не постачають препарат з назвою drugName.
     *
     * @param drugName Name of drug, which is not supplied
     * @return List of cities
     */
    @Query(value = "SELECT * FROM cities WHERE cities.id IN "
            + "( SELECT manufacturers.city_id FROM manufacturers WHERE manufacturers.id NOT IN "
            + "( SELECT drugs.manufacturer_id FROM drugs WHERE drugs.name = ?1) )",
            nativeQuery = true)
    List<City> findCitiesManufacturersNotSupplyingDrug(String drugName);

    //    Знайти назви міст виробників, які постачають усі препарати
    @Query(value = "SELECT cities.name FROM cities WHERE cities.id IN "
            + "( SELECT M.city_id FROM manufacturers M WHERE "
            + "NOT EXISTS( (SELECT drugs.id FROM drugs WHERE drugs.manufacturer_id = M.id) EXCEPT (SELECT drugs.id FROM drugs) ) "
            + "AND NOT EXISTS( (SELECT drugs.id FROM drugs) EXCEPT (SELECT drugs.id FROM drugs WHERE drugs.manufacturer_id = M.id) ) )",
            nativeQuery = true)
    List<City> findCitiesManufacturersSupplyAllDrugs();

}
