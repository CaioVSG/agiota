package com.ufape.agiota.comunication.controllers;

import com.ufape.agiota.comunication.dto.customer.CustomerRequest;
import com.ufape.agiota.comunication.dto.customer.CustomerResponse;
import com.ufape.agiota.negocio.facade.Facade;
import com.ufape.agiota.negocio.models.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer") @RequiredArgsConstructor
public class CustomerController {
    final private ModelMapper modelMapper;
    final private Facade facade;

    @PostMapping
    public ResponseEntity<CustomerResponse> saveCustomer(@Valid @RequestBody CustomerRequest customer) throws AccessDeniedException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        if (facade.checkDuplicatedUser(principal.getSubject())){throw new AccessDeniedException("User already exists");}
        CustomerResponse response = new CustomerResponse(facade.saveCustomer(customer.convertToEntity()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AGIOTA', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomer(@PathVariable Long id){
        CustomerResponse response = new CustomerResponse(facade.findCustomer(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AGIOTA', 'CUSTOMER')")
    @GetMapping
    public ResponseEntity<Iterable<CustomerResponse>> findAllCustomers(){
        Iterable<CustomerResponse> response = facade.findAllCustomers().stream().map(CustomerResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest entity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        Customer customer = facade.findCustomer(id);
        if(!principal.getSubject().equals(customer.getIdKc())){throw new AccessDeniedException("User not allowed");}
        modelMapper.map(entity, customer);
        CustomerResponse response = new CustomerResponse(facade.saveCustomer(customer));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        if(!principal.getSubject().equals(facade.findCustomer(id).getIdKc())){throw new AccessDeniedException("User not allowed");}
        facade.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
