package com.ufape.agiota.comunication.dto.agiota;


import com.ufape.agiota.comunication.dto.adress.AdressRequest;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AgiotaUpdateRequest {
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String name;

    @NotBlank(message = "email é obrigatório")
    @Email(message = "Email inválido")
    private String email;


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
}
