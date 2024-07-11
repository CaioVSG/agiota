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


    private List<Installments> gerarParcelas(){
        double jurusDia = fees/30;
        BigDecimal valorParcela = this.value.divide(new BigDecimal(frequency.valor), RoundingMode.UP);
        List<Installments> parcelas = new ArrayList<>();
        Calendar dataPagamento = (GregorianCalendar) initialDate.clone();

        for(int i=1; i <= numberInstallments; i++){
            Installments parcela = new Installments();
            parcela.setStatus(false);
            dataPagamento.add(Calendar.DATE,frequency.valor);
            parcela.setPaymentDate(dataPagamento);
            valorParcela = valorParcela.multiply(new BigDecimal(jurusDia*frequency.valor));
            parcela.setValue(valorParcela);
            parcelas.add(parcela);

        }
        return parcelas;

    }
}
