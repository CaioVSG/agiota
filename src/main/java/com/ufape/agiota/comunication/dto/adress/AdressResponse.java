package com.ufape.agiota.comunicacao.dto.adress;

import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Adress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AdressResponse {
    private String road;
    private String place;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String cep;

    public AdressResponse(Adress obj){
        if(obj == null) throw new IllegalArgumentException("Objeto não pode ser nulo");
        else{
            ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
            modelMapper.map(obj, this);
        }

    }
}
