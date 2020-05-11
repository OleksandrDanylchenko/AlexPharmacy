package com.alexd.AlexPharmacy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
@SpringBootApplication
public class AlexPharmacyApplication {

    /**
     * Method, which boots up entire app.
     *
     * @param args - command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(AlexPharmacyApplication.class, args);
    }

}
