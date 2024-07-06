package com.ufape.agiota.comunicacao.dto.endereco;

import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class EnderecoResponse {
    private Long id;
    private String rua;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public EnderecoResponse(Endereco obj){
        if(obj == null) throw new IllegalArgumentException("Objeto não pode ser nulo");
        else{
            ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
            modelMapper.map(obj, this);
        }

    }
}
