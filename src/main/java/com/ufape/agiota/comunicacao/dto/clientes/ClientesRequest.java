package com.ufape.agiota.comunicacao.dto.clientes;

import com.ufape.agiota.comunicacao.dto.endereco.EnderecoRequest;
import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Cliente;
import com.ufape.agiota.negocio.models.Endereco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ClientesRequest {
    private String nome;
    private String cpf;
    private String telefone;
    private EnderecoRequest endereco;
    private String profissao;
    private String localTrabalho;
    private String telefoneTrabalho;

    public Cliente convertToEntity() {
        ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
        Cliente obj = modelMapper.map(this, Cliente.class);
        obj.setEndereco(modelMapper.map(this.endereco, Endereco.class));
        return obj;
    }
}
