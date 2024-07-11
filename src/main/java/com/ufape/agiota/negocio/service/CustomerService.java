package com.ufape.agiota.negocio.service;

import com.ufape.agiota.dados.repository.RepositoryCostumer;
import com.ufape.agiota.negocio.models.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @RequiredArgsConstructor
public class CustomerService implements CustomerServiceInterface {
    final private RepositoryCostumer repositoryCostumer;

    @Transactional
    @Override
    public Customer save(Customer customer) { return repositoryCostumer.save(customer); }

    @Override
    public void delete(Long id) {
        repositoryCostumer.deleteById(id);
    }

    @Override
    public Customer find(Long id) {
        return repositoryCostumer.findById(id).orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return repositoryCostumer.findAll();
    }
}
