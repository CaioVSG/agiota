package com.ufape.agiota.dados.repositorios;

import com.ufape.agiota.negocio.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCliente extends JpaRepository<Cliente, Long> {
}