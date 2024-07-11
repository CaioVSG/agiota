package com.ufape.agiota.comunication.dto.customer;

import com.ufape.agiota.comunication.dto.adress.AdressRequest;
import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Adress;
import com.ufape.agiota.negocio.models.Customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CustomerRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter entre 10 e 11 dígitos")
    private String phone;

    private AdressRequest adress;

    @NotNull(message = "Informar a sua ocupação é obrigatório")
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
