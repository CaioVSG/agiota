package com.ufape.agiota.negocio.service;

import com.ufape.agiota.negocio.models.Customer;

import java.util.List;

public interface CustomerServiceInterface {
    Customer save(Customer customer);

    void delete(Long id, String idSession);

    Customer find(Long id);

    List<Customer> findAll();

    Customer update(Customer customer, String idSession);
}
