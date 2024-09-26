package com.ufape.agiota.negocio.service;

import com.ufape.agiota.negocio.models.*;

import java.util.List;

public interface BorrowingServiceInterface {
    Borrowing save(Borrowing borrowing);

    void delete(Long id);

    Borrowing find(Long id);

    Borrowing denied(Long id);

    Borrowing accept(Long id);

    Borrowing request(Long id, Customer customer);

    List<Borrowing> findAvailable();

    List<Borrowing> findAgiotaBorrowings(Long id);

    List<Borrowing> findCustomerBorrowings(Long id);

    Payment pay(Long id, Long installid, String sessionId);

    Borrowing evaluateCustomerBorrowing(Long id, Avaliacao avaliacao, String SessionId);

    Borrowing evaluateAgiotaBorrowing(Long id, Avaliacao avaliacao, String sessionId);

    List<Installments> listInstallments(Long id);

    Avaliacao avaliacaoByCustomerId (Long id);

    Avaliacao avaliacaoByAgiotaId(Long id);
}
