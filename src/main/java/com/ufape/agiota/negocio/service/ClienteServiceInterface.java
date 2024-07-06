package com.ufape.agiota.negocio.service;

import com.ufape.agiota.negocio.models.Cliente;

import java.util.List;

public interface ClienteServiceInterface {
    Cliente salvar(Cliente cliente);

    void deletar(Long id);

    Cliente buscar(Long id);

    List<Cliente> buscarTodos();
}
