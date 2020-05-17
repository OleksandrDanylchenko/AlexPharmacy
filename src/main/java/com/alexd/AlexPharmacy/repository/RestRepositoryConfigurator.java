package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.City;
import com.alexd.AlexPharmacy.domain.Client;
import com.alexd.AlexPharmacy.domain.Disease;
import com.alexd.AlexPharmacy.domain.Drug;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Service;

/**
 * Configuration class.
 * Purpose of it is to tell Spring to return the data model instance IDs
 * with the object serializations.
 */
@Service
public class RestRepositoryConfigurator implements RepositoryRestConfigurer {

    /**
     * ID exposing method.
     */
    @Override
    public void configureRepositoryRestConfiguration(@NotNull final RepositoryRestConfiguration config) {
        config.exposeIdsFor(Client.class);
        config.exposeIdsFor(City.class);
        config.exposeIdsFor(Disease.class);
        config.exposeIdsFor(Drug.class);
    }

}
