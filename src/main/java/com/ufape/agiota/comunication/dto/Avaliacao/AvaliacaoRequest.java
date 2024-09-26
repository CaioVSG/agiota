package com.ufape.agiota.comunication.dto.Avaliacao;

import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Avaliacao;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoRequest {
    @Min(value = 0, message = "Informe uma nota entre 0 e 5")
    @Max(value = 5, message = "Informe uma nota entre 0 e 5")
    private double nota;

    public Avaliacao convertToEntity(){
        ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
        return modelMapper.map(this,Avaliacao.class);
    }
}
