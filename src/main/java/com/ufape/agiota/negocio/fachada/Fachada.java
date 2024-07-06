package com.ufape.agiota.negocio.fachada;

import com.ufape.agiota.negocio.models.Cliente;
import com.ufape.agiota.negocio.service.ClienteServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Fachada {
    final private ClienteServiceInterface clienteService;

    // ================== Cliente ================== //
    public Cliente salvarCliente(Cliente cliente) {
       return clienteService.salvar(cliente);
    }

    public void deletarCliente(Long id) {
        clienteService.deletar(id);
    }

    public Cliente buscarCliente(Long id) {
        return clienteService.buscar(id);
    }

    public List<Cliente> buscarTodosClientes() {
        return clienteService.buscarTodos();
    }
}
