package com.ufape.agiota.negocio.models;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Agiota extends User{
    private double fees;
    private String billingMethod;
}

