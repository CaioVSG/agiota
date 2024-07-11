package com.ufape.agiota.comunication.dto.agiota;

import org.modelmapper.ModelMapper;

import com.ufape.agiota.comunication.dto.adress.AdressResponse;
import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Agiota;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AgiotaResponse {
    private Long id;
    private String name;
    private String cpf;
    private String phone;
    private  AdressResponse adress;
    private double fees;
    private String billingMethod;

    public AgiotaResponse(Agiota obj){
        if(obj == null) throw new IllegalArgumentException("Objeto não pode ser nulo");
        else{
            ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
            modelMapper.map(obj, this);
        }

    }


}
