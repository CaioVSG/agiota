package com.ufape.agiota.negocio.service;

import com.ufape.agiota.negocio.models.Customer;

import java.util.List;

public interface CustomerServiceInterface {
    Customer save(Customer customer);

    void delete(Long id);

    Customer find(Long id);

    List<Customer> findAll();
}
