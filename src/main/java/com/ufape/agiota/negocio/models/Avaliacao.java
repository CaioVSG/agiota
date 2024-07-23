package com.ufape.agiota.negocio.models;

import com.ufape.agiota.negocio.enums.Avaliado;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int nota;
    @Enumerated(EnumType.STRING)
    private Avaliado avaliado;
}
