package com.alexd.AlexPharmacy.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
public class Client {

    /**
     * Identification number of client.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * First name of clients.
     */
    @NotNull
    @NotEmpty
    private String firstName;

    /**
     * Last name of clients.
     */
    @NotNull
    @NotEmpty
    private String lastName;

    /**
     * Birthday of clients.
     */
    @NotNull
    private LocalDate birthday;

}
