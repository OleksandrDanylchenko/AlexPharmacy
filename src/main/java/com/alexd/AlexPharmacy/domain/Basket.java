package com.alexd.AlexPharmacy.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "baskets")
@Data
@NoArgsConstructor
public class Basket implements PharmacyDomain {

    /**
     * Identification number of basket.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Client, who bought drug.
     */
    @ManyToOne
    @JoinColumn(name = "clientId")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Client client;

    /**
     * Drug, which bought by client.
     */
    @ManyToOne
    @JoinColumn(name = "drugId")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Drug drug;

    /**
     * Date and time of buying.
     */
    @NotNull(message = "Час покупки не може бути відсутнім")
    private LocalDateTime buyingTime;

}
