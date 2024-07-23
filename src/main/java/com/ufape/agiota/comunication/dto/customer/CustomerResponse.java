package com.ufape.agiota.comunication.dto.customer;

import com.ufape.agiota.comunication.dto.adress.AdressResponse;
import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CustomerResponse {
    private Long id;
    private String name;
    private String cpf;
    private String phone;
    private AdressResponse adress;
    private String occupation;
    private String workplace;
    private String workPhone;

    public CustomerResponse(Customer obj){
        if(obj == null) throw new IllegalArgumentException("Objeto não pode ser nulo");
        else{
            ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
            modelMapper.map(obj, this);
        }

    }

}
