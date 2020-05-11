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
public class Client implements PharmacyDomain {

    /**
     * Identification number of client.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * First name of clients.
     */
    @NotNull(message = "Ім'я клієнту не може бути відсутнім")
    @NotEmpty(message = "Ім'я клієнту не може бути пустим")
    private String firstName;

    /**
     * Last name of clients.
     */
    @NotNull(message = "Прізвище клієнту не може бути відсутнім")
    @NotEmpty(message = "Прізвище клієнту не може бути пустим")
    private String lastName;

    /**
     * Birthday of clients.
     */
    @NotNull(message = "День народження клієнту не може бути відсутнім")
    private LocalDate birthday;

}
