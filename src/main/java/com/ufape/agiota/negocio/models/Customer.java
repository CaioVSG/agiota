package com.ufape.agiota.negocio.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Customer extends User{
    private String occupation;
    private String workplace;
    private String workPhone;

}
