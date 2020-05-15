package com.alexd.AlexPharmacy.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "drugs")
@Data
@NoArgsConstructor
public class Drug implements PharmacyDomain {

    /**
     * Identification number of drug.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Name of drug.
     */
    @NotNull(message = "Назва препарату не може бути відсутньою")
    @NotEmpty(message = "Назва препарату не може бути пустою")
    private String name;

    /**
     * Manufacturer of drug.
     */
    @NotNull(message = "Виробник не може бути відсутнім")
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    /**
     * Usage instruction for drug.
     */
    @NotNull(message = "Інструкція не може бути відсутньою")
    @NotEmpty(message = "Інструкція не може бути пустою")
    private String instruction;

    /**
     * List of diseases treated with this drug.
     */
    @JsonIgnoreProperties("drugs")
    @ManyToMany
    @JoinTable(name = "disease_drug",
            joinColumns = @JoinColumn(name = "drug_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id", referencedColumnName = "id"))
    private List<Disease> diseases;

}
