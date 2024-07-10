package com.ufape.agiota.negocio.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Calendar;

@Entity
public class Lembrete {

    @Id
    private long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar data;
    private String mensagem;

}
