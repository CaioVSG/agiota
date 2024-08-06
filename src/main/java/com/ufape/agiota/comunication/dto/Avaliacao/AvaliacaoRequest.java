package com.ufape.agiota.comunication.dto.Avaliacao;

import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.enums.Avaliado;
import com.ufape.agiota.negocio.models.Avaliacao;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    private int nota;
    @NotNull(message = "Informar o valor do Juros é obrigatório")
    private Avaliado avaliado;

    public Avaliacao convertToEntity(){
        ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
        Avaliacao obj = modelMapper.map(this,Avaliacao.class);
        return obj;
    }
}
