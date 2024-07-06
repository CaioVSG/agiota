package com.ufape.agiota.comunicacao.dto.clientes;

import com.ufape.agiota.comunicacao.dto.endereco.EnderecoResponse;
import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ClientesResponse {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private EnderecoResponse endereco;
    private String profissao;
    private String localTrabalho;
    private String telefoneTrabalho;

    public ClientesResponse(Cliente obj){
        if(obj == null) throw new IllegalArgumentException("Objeto não pode ser nulo");
        else{
            ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
            modelMapper.map(obj, this);
        }

    }

}
