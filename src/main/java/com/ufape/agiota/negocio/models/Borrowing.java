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
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
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
    private int payday;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar initialDate;
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


    @Override
    public final boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass)
            return false;
        Borrowing borrowing = (Borrowing) o;
        return getId() != null && Objects.equals(getId(), borrowing.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }


}
