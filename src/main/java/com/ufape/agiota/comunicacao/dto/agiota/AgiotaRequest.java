package com.ufape.agiota.comunicacao.dto.agiota;

import com.ufape.agiota.comunicacao.dto.adress.AdressRequest;
import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Adress;
import com.ufape.agiota.negocio.models.Agiota;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AgiotaRequest {
    private String name;
    private String cpf;
    private String phone;
    private AdressRequest adress;
    private double fees;
    private String billingMethod;

   public Agiota convertToEntity() {
        ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
        Agiota obj = modelMapper.map(this, Agiota.class);
        obj.setAdress(modelMapper.map(this.adress, Adress.class));
        return obj;
    }
    
}
