package com.alexd.AlexPharmacy.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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
    @Past(message = "День народження не може бути після теперішньої дати")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

}
