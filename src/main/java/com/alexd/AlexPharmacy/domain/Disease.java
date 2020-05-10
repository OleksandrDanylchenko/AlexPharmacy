package com.alexd.AlexPharmacy.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "diseases")
@Data
@NoArgsConstructor
public class Disease {

    /**
     * Identification number of disease.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Name of disease.
     */
    @Column(unique = true)
    @NotNull(message = "Назва хвороби не може бути відсутньою")
    @NotEmpty(message = "Назва хвороби не може бути пустою")
    private String name;

    /**
     * List of drugs, which can cure specified disease.
     */
    @ManyToMany(mappedBy = "diseases")
    private List<Drug> drugs;

}
