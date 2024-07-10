package com.ufape.agiota.negocio.models;

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
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double fees;
    private BigDecimal value;
    private Integer numberInstallments;
    private Date initialDate;
    private Date payday;
    @Enumerated (EnumType.STRING)
    private Frequency frequency;
    @Enumerated (EnumType.STRING)
    private Status status;
    private Double discount;

    @ManyToOne
    private Customer customer;
    // add uma variavel p agiota
    @OneToMany
    @Cascade(CascadeType.ALL)
    private List<Installments> installmentsList;
}
