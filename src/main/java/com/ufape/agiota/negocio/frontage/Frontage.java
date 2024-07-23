package com.ufape.agiota.negocio.frontage;


import com.ufape.agiota.negocio.models.Agiota;
import com.ufape.agiota.negocio.models.Customer;
import com.ufape.agiota.negocio.service.AgiotaServiceInterface;
import com.ufape.agiota.negocio.models.Borrowing;
import com.ufape.agiota.negocio.models.Customer;
import com.ufape.agiota.negocio.service.BorrowingServiceInterface;
import com.ufape.agiota.negocio.service.CustomerServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Frontage {
    final private CustomerServiceInterface clienteService;
    final private AgiotaServiceInterface agiotaService;
    final private BorrowingServiceInterface borrowingService;

    // ================== Customer ================== //

    public Customer saveCustomer(Customer customer) { return clienteService.save(customer); }

    public void deleteCustomer(Long id) {
        clienteService.delete(id);
    }

    public Customer findCustomer(Long id) {
        return clienteService.find(id);
    }

    public List<Customer> findAllCustomers() {
        return clienteService.findAll();
    }

    // ================== Agiota ================== //

    public Agiota saveAgiota(Agiota agiota) {
        return agiotaService.save(agiota);
    }

    public void deleteAgiota(Long id) {
        agiotaService.delete(id);
    }

    public Agiota findAgiota(Long id) {
        return agiotaService.find(id);
    }

    public List<Agiota> findAllAgiotas() {
        return agiotaService.findAll();
    }

    public Agiota updateAgiota(Long id, Agiota agiota) {
        return agiotaService.update(id, agiota);
    }

    // =================== Borrowing =================== //

    public Borrowing saveBorrowing(Borrowing borrowing) { return borrowingService.save(borrowing); }

    public Borrowing findBorrowing(Long id) { return borrowingService.find(id); }

    public Borrowing deniedBorrowing(Long id) { return borrowingService.find(id); }

    public Borrowing acceptBorrowing(Long id) { return borrowingService.find(id); }

    public Borrowing evaluateCustomerBorrowing(Long id, int nota) {return borrowingService.evaluateCustomerBorrowing(id,nota);}

    public Borrowing evaluateAgiotaBorrowing(Long id, int nota) { return borrowingService.evaluateAgiotaBorrowing(id,nota);}
}
