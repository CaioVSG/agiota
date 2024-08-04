package com.ufape.agiota.negocio.service;

import com.ufape.agiota.negocio.models.Borrowing;
import com.ufape.agiota.negocio.models.Customer;
import com.ufape.agiota.negocio.models.Installments;
import com.ufape.agiota.negocio.models.Payment;

import java.util.List;

public interface BorrowingServiceInterface {
    Borrowing save(Borrowing borrowing);

    void delete(Long id);

    Borrowing find(Long id);

    Borrowing denied(Long id);

    Borrowing accept(Long id);

    Borrowing evaluateCustomerBorrowing(Long id, int nota);

    Borrowing evaluateAgiotaBorrowing(Long id, int nota);

    Payment pay(Long id, Long installid);

    List<Installments> listInstallments(Long id);

}
