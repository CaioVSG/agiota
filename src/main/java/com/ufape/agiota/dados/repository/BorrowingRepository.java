package com.ufape.agiota.dados.repository;

import com.ufape.agiota.negocio.models.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
}
