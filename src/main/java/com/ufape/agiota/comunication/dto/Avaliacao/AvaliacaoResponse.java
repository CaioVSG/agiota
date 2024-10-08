package com.ufape.agiota.comunication.dto.Avaliacao;


import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.enums.Avaliado;
import com.ufape.agiota.negocio.models.Avaliacao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AvaliacaoResponse {

    private double nota;
    @Enumerated(EnumType.STRING)
    private Avaliado avaliado;

    public AvaliacaoResponse(Avaliacao obj) {
        if (obj == null) throw new IllegalArgumentException("Objeto não pode ser nulo");
        else{
            ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
            modelMapper.map(obj, this);
        }
    }
}
