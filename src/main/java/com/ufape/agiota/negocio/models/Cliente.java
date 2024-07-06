package com.ufape.agiota.negocio.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Cliente extends Usuario{
    private String profissao;
    private String localTrabalho;
    private String telefoneTrabalho;

}
