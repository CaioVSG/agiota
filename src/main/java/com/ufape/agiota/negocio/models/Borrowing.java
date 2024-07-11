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
import java.text.SimpleDateFormat;
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

    public void aceitar(){

        this.initialDate = new GregorianCalendar();
        this.status = Status.ANDAMENTO;
        this.discount = 0.0;
        this.installmentsList = this.gerarParcelas();

    }

    private List<Installments> gerarParcelas(){
        double jurusDia = fees/100/30;
        BigDecimal valorParcela = this.value.divide(new BigDecimal(numberInstallments), RoundingMode.UP);
        List<Installments> parcelas = new ArrayList<>();
        Calendar dataPagamento = (GregorianCalendar) initialDate.clone();
        dataPagamento.set(Calendar.DAY_OF_MONTH, payday);
        long hoje = new Date().getTime();
        for(int i=1; i <= numberInstallments; i++){
            Installments parcela = new Installments();
            parcela.setStatus(false);
            GregorianCalendar temp = (GregorianCalendar) dataPagamento.clone();
            if(frequency == Frequency.MENSAL){
                temp.add(Calendar.MONTH, i);
                parcela.setPaymentDate(temp);
            }else if(frequency == Frequency.QUINZENAL){
                temp.add(Calendar.DATE, 15*i);
                parcela.setPaymentDate(temp);
            }else{
                temp.add(Calendar.WEEK_OF_YEAR, i);
                parcela.setPaymentDate(temp);
            }

            long proximoPagament = parcela.getPaymentDate().getTimeInMillis();
            long l = proximoPagament - hoje;
            hoje = proximoPagament;
            int dias = Math.round(l /86400000f);
            valorParcela = valorParcela.multiply(new BigDecimal(1.0+jurusDia*dias));
            parcela.setValue(valorParcela);
            parcelas.add(parcela);

        }
        return parcelas;

    }
}
