package com.ufape.agiota.negocio.service;

import com.ufape.agiota.dados.repositorios.RepositorioCliente;
import com.ufape.agiota.negocio.models.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class ClienteService implements ClienteServiceInterface {
    final private RepositorioCliente repositorioCliente;

    @Transactional
    @Override
    public Cliente salvar(Cliente cliente) {

        return repositorioCliente.save(cliente);
    }

    @Override
    public void deletar(Long id) {
        repositorioCliente.deleteById(id);
    }

    @Override
    public Cliente buscar(Long id) {
        return repositorioCliente.findById(id).orElse(null);
    }

    @Override
    public List<Cliente> buscarTodos() {
        return repositorioCliente.findAll();
    }
}
