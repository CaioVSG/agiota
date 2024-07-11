package com.ufape.agiota.comunication.dto.customer;

import com.ufape.agiota.comunication.dto.adress.AdressRequest;
import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Adress;
import com.ufape.agiota.negocio.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CustomerRequest {
    private String name;
    private String cpf;
    private String phone;
    private AdressRequest adress;
    private String occupation;
    private String workplace;
    private String workPhone;

    public Customer convertToEntity() {
        ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
        Customer obj = modelMapper.map(this, Customer.class);
        obj.setAdress(modelMapper.map(this.adress, Adress.class));
        return obj;
    }
}
