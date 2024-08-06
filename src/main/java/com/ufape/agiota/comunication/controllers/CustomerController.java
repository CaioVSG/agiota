package com.ufape.agiota.comunication.controllers;

import com.ufape.agiota.comunication.dto.customer.CustomerRequest;
import com.ufape.agiota.comunication.dto.customer.CustomerResponse;
import com.ufape.agiota.negocio.frontage.Frontage;
import com.ufape.agiota.negocio.models.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer") @RequiredArgsConstructor
public class CustomerController {
    final private ModelMapper modelMapper;
    final private Frontage frontage;

    @PostMapping
    public ResponseEntity<CustomerResponse> saveCustomer(@Valid @RequestBody CustomerRequest customer){
       CustomerResponse response = new CustomerResponse(frontage.saveCustomer(customer.convertToEntity()));
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomer(@PathVariable Long id){
        CustomerResponse response = new CustomerResponse(frontage.findCustomer(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<CustomerResponse>> findAllCustomers(){
        Iterable<CustomerResponse> response = frontage.findAllCustomers().stream().map(CustomerResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest entity){
        Customer customer = frontage.findCustomer(id);
        modelMapper.map(entity, customer);
        CustomerResponse response = new CustomerResponse(frontage.saveCustomer(customer));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        frontage.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
