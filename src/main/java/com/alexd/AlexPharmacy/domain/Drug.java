package com.alexd.AlexPharmacy.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "drugs")
@Data
@NoArgsConstructor
public class Drug {

    /**
     * Identification number of drug.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Name of drug.
     */
    @NotNull
    @NotEmpty
    private String name;

    /**
     * Usage instruction for drug.
     */
    @NotNull
    @NotEmpty
    private String instruction;

    /**
     * List of diseases treated with this drug.
     */
    @ManyToMany
    @JoinTable(name = "disease_drug",
            joinColumns = @JoinColumn
                    (name = "disease_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn
                    (name = "drug_id", referencedColumnName = "id"))
    private List<Disease> diseases;

}
