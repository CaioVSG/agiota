package com.ufape.agiota.comunication.dto.adress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AdressRequest {
    private String road;
    private String place;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String cep;


}