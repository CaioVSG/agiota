package com.ufape.agiota.negocio.service;

import com.ufape.agiota.negocio.models.Borrowing;
import com.ufape.agiota.negocio.models.Customer;

import java.util.List;

public interface BorrowingServiceInterface {
    Borrowing save(Borrowing customer);

    void delete(Long id);

    Borrowing find(Long id);
}
