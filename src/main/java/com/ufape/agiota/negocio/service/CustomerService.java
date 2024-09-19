package com.ufape.agiota.negocio.service;

import com.ufape.agiota.dados.repository.CustomerRepository;
import com.ufape.agiota.negocio.models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class CustomerService implements CustomerServiceInterface {
    final private CustomerRepository customerRepository;

    @Transactional
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer); }

    @Transactional
    @Override
    public void delete(Long id, String idSession) {
        if (!find(id).getIdKc().equals(idSession)) throw new AccessDeniedException("User not allowed");
        customerRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Customer update(Customer customer, String idSession) {
        if (!customer.getIdKc().equals(idSession)) throw new AccessDeniedException("User not allowed");
        return customerRepository.save(customer);
    }

    @Override
    public Customer find(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
