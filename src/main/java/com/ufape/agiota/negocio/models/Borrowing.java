package com.ufape.agiota.negocio.models;

import com.ufape.agiota.negocio.enums.Avaliado;
import com.ufape.agiota.negocio.enums.Frequency;
import com.ufape.agiota.negocio.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Borrowing {
    //loan
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double fees;
    private BigDecimal value;
    private int numberInstallments;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar initialDate;
    private int payday;
    @Enumerated (EnumType.STRING)
    private Frequency frequency;
    @Enumerated (EnumType.STRING)
    private Status status;
    private double discount;

    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Agiota agiota;

    @OneToMany
    @Cascade(CascadeType.ALL)
    private List<Installments> installmentsList;

    @OneToMany
    @Cascade(CascadeType.ALL)
    private  List<Avaliacao> listaAvaliacoes;


    public Borrowing(BigDecimal value, int numberInstallments, int payday, Frequency frequency, Customer customer, Agiota agiota) {

        this.value = value;
        this.numberInstallments = numberInstallments;
        this.payday = payday;
        this.frequency = frequency;
        this.fees = agiota.getFees();
        this.customer = customer;
        this.agiota = agiota;
        this.status = Status.SOLICITADO;
    }


}
