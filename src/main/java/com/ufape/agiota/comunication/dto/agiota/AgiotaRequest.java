package com.ufape.agiota.comunication.dto.agiota;

import com.ufape.agiota.comunication.dto.adress.AdressRequest;
import com.ufape.agiota.comunication.dto.tags.Username;
import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Adress;
import com.ufape.agiota.negocio.models.Agiota;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AgiotaRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    @NotBlank(message = "Username é obrigatório")
    @Username
    private String username;

    @NotBlank(message = "email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String password;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "Telefone deve ter entre 10 e 11 dígitos")
    private String phone;

    private AdressRequest adress;

    @NotNull(message = "Informar o valor do Juros é obrigatório")
    private double fees;

    @NotBlank(message = "Método de cobrança é obrigatório")
    private String billingMethod;

   public Agiota convertToEntity() {
        ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
        Agiota obj = modelMapper.map(this, Agiota.class);
        obj.setAdress(modelMapper.map(this.adress, Adress.class));
        return obj;
    }
    
}
