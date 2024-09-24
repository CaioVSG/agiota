package com.ufape.agiota.comunication.controllers;

import com.ufape.agiota.comunication.dto.customer.CustomerRequest;
import com.ufape.agiota.comunication.dto.customer.CustomerResponse;
import com.ufape.agiota.comunication.dto.customer.CustomerUpdateRequest;
import com.ufape.agiota.negocio.facade.Facade;
import com.ufape.agiota.negocio.models.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer") @RequiredArgsConstructor
public class CustomerController {
    final private ModelMapper modelMapper;
    final private Facade facade;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> saveCustomer(@Valid @RequestBody CustomerRequest customer) throws AccessDeniedException{
        CustomerResponse response = new CustomerResponse(facade.saveCustomer(customer.convertToEntity(), customer.getPassword()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomer(@PathVariable Long id){
        CustomerResponse response = new CustomerResponse(facade.findCustomer(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<CustomerResponse>> findAllCustomers(){
        Iterable<CustomerResponse> response = facade.findAllCustomers().stream().map(CustomerResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerUpdateRequest entity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        Customer customer = facade.findCustomer(id);
        modelMapper.map(entity, customer);
        CustomerResponse response = new CustomerResponse(facade.updateCustomer(customer, principal.getSubject()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        facade.deleteCustomer(id, principal.getSubject());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("current")
    public ResponseEntity<CustomerResponse> currentCustomer(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        CustomerResponse response = new CustomerResponse(facade.findCustomerByIdKc(principal.getSubject()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
