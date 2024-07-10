package com.ufape.agiota.dados.repository;

import com.ufape.agiota.negocio.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReposioryCostumer extends JpaRepository<Customer, Long> {
}