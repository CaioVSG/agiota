package com.ufape.agiota.comunication.dto.adress;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AdressRequest {
    @NotBlank(message = "Rua é obrigatória")
    @Size(min = 2, max = 100, message = "Rua deve ter entre 2 e 100 caracteres")
    private String road;

    @NotBlank(message = "Local é obrigatório")
    private String place;

    @NotBlank(message = "Número é obrigatório")
    private String number;

    @NotBlank(message = "Bairro é obrigatório")
    private String neighborhood;

    @NotBlank(message = "Cidade é obrigatória")
    private String city;

    @NotBlank(message = "Estado é obrigatório")
    private String state;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve ter 8 dígitos")
    private String cep;


}