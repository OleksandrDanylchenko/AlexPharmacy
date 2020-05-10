package com.alexd.AlexPharmacy.repository;

import com.alexd.AlexPharmacy.domain.Client;
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
    public void configureRepositoryRestConfiguration(
            final RepositoryRestConfiguration config) {
        config.exposeIdsFor(Client.class);
    }

}
