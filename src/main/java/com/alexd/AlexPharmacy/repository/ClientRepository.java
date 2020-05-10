package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@SuppressWarnings("unused")
@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Method for finding all clients with given last name.
     *
     * @param lastName - criteria for finding all clients with same last name
     * @return returns list of client with given last name
     */
    Iterable<Client> findAllByLastName(String lastName);

}
