package com.ufape.agiota.negocio.service;

import com.ufape.agiota.dados.repository.ReposioryCostumer;
import com.ufape.agiota.negocio.models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class CustomerService implements CustomerServiceInterface {
    final private ReposioryCostumer reposioryCostumer;

    @Transactional
    @Override
    public Customer save(Customer customer) {
        return reposioryCostumer.save(customer);
    }

    @Override
    public void delete(Long id) {
        reposioryCostumer.deleteById(id);
    }

    @Override
    public Customer find(Long id) {
        return reposioryCostumer.findById(id).orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return reposioryCostumer.findAll();
    }
}
