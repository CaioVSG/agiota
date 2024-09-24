package com.ufape.agiota.negocio.facade;


import com.ufape.agiota.comunication.dto.Auth.TokenResponse;
import com.ufape.agiota.comunication.dto.borrowing.BorrowingRequest;
import com.ufape.agiota.negocio.models.*;
import com.ufape.agiota.negocio.service.*;
import com.ufape.agiota.negocio.models.Customer;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Facade {
    final private CustomerServiceInterface customerService;
    final private AgiotaServiceInterface agiotaService;
    final private BorrowingServiceInterface borrowingService;
    final private KeycloakService keycloakService;

    // ================== Keycloak ================== //
    public TokenResponse login(String username, String password) throws Exception {
        return keycloakService.login(username, password);
    }

    // ================== Customer ================== //

    @Transactional
    public Customer saveCustomer(Customer customer, String password) {
        String userKcId = null;
        try {
            Response keycloakResponse = keycloakService.createUser(customer.getUsername(), customer.getEmail(), password, "customer");
            if (keycloakResponse.getStatus() == 201) {
                userKcId = keycloakService.getUserId(customer.getUsername());
                customer.setIdKc(userKcId);
                return customerService.save(customer);
            }
        }catch (Exception e){
            if (userKcId != null) keycloakService.deleteUser(userKcId);
        }
        throw new RuntimeException("Error creating user");
    }

    @Transactional
    public Customer updateCustomer(Customer customer, String idSession) {
        try {
            Customer newCustomer =  customerService.update(customer, idSession);
            keycloakService.updateUser(newCustomer.getIdKc(), customer.getEmail());
            return newCustomer;
        }catch (Exception e){
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteCustomer(Long id, String idSession) {
        try {
            customerService.delete(id, idSession);
            keycloakService.deleteUser(idSession);
        }catch (Exception e){
            throw new RuntimeException("Error deleting user");
        }

    }

    public Customer findCustomer(Long id) {
        return customerService.find(id);
    }

    public List<Customer> findAllCustomers() {
        return customerService.findAll();
    }

    public Customer findCustomerByIdKc(String id) {
        return customerService.findByIdKc(id);
    }

    // ================== Agiota ================== //

    @Transactional
    public Agiota saveAgiota(Agiota agiota, String password) {
        String userKcId = null;
        try {
            Response keycloakResponse = keycloakService.createUser(agiota.getUsername(), agiota.getEmail(), password, "agiota");
            if (keycloakResponse.getStatus() == 201) {
                userKcId = keycloakService.getUserId(agiota.getUsername());
                agiota.setIdKc(userKcId);
                return agiotaService.save(agiota);
            }
        }catch (Exception e){
            if (userKcId != null) keycloakService.deleteUser(userKcId);
        }
        throw new RuntimeException("Error creating user");
    }

    @Transactional
    public void deleteAgiota(Long id, String idSession) {
        try {
            agiotaService.delete(id, idSession);
        }catch (Exception e){
            throw new RuntimeException("Error deleting user");
        }
        try {
            keycloakService.deleteUser(idSession);
        }catch (Exception e){
            throw new RuntimeException("Error deleting user");
        }
    }

    public Agiota findAgiota(Long id) {
        return agiotaService.find(id);
    }

    public List<Agiota> findAllAgiotas() {
        return agiotaService.findAll();
    }

    public Agiota findAgiotaByIdKc(String id) {
        return agiotaService.findByIdKc(id);
    }

    @Transactional
    public Agiota updateAgiota(Agiota agiota, String idSession) {
        try {
            Agiota newAgiota =  agiotaService.update(agiota, idSession);
            keycloakService.updateUser(newAgiota.getIdKc(), agiota.getEmail());
            return newAgiota;
        }catch (Exception e){
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
    }

    // =================== Borrowing =================== //

    public Borrowing saveBorrowing(Borrowing borrowing) { return borrowingService.save(borrowing); }

    public Borrowing findBorrowing(Long id) { return borrowingService.find(id); }

    public Borrowing deniedBorrowing(Long id) { return borrowingService.find(id); }

    public Borrowing acceptBorrowing(Long id) { return borrowingService.accept(id); }

    public Borrowing evaluateCustomerBorrowing(Long id, Avaliacao avaliacao) {return borrowingService.evaluateCustomerBorrowing(id,avaliacao);}

    public Borrowing evaluateAgiotaBorrowing(Long id, Avaliacao avaliacao) { return borrowingService.evaluateAgiotaBorrowing(id,avaliacao);}

    public Payment payBorrowing(Long id, Long installid) { return borrowingService.pay(id, installid); }

    public List<Installments> listInstallments(Long id) { return borrowingService.listInstallments(id); }

    public Borrowing requestBorrowing(Long id, BorrowingRequest request) {
        Customer customer = customerService.find(request.getCustomerId());
        return borrowingService.request(id, customer); }

}
