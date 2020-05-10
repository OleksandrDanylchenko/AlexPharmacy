package com.alexd.AlexPharmacy.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
public class City {

    /**
     * Identification number of city.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Name of city.
     */
    @Column(unique = true)
    @NotNull
    @NotEmpty
    private String name;

}
