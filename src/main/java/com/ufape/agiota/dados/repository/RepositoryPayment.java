package com.ufape.agiota.dados.repository;

import com.ufape.agiota.negocio.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryPayment extends JpaRepository<Payment, Long> {
}