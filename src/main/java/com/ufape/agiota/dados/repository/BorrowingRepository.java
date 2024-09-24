package com.ufape.agiota.dados.repository;

import com.ufape.agiota.negocio.enums.Status;
import com.ufape.agiota.negocio.models.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    List<Borrowing> findByStatus(Status status);
    List<Borrowing> findByAgiotaId(Long id);
    List<Borrowing> findByCustomerId(Long id);
}
