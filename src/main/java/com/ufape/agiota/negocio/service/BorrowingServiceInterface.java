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

    Borrowing evaluateCustomerBorrowing(Long id, Avaliacao avaliacao);

    Borrowing evaluateAgiotaBorrowing(Long id, Avaliacao avaliacao);

    List<Borrowing> findAvailable();

    List<Borrowing> findAgiotaBorrowings(Long id);

    List<Borrowing> findCustomerBorrowings(Long id);

    Payment pay(Long id, Long installid);

    List<Installments> listInstallments(Long id);

}
