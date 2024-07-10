package com.ufape.agiota.comunicacao.controllers;

import com.ufape.agiota.comunicacao.dto.customer.CustomerRequest;
import com.ufape.agiota.comunicacao.dto.customer.CustomerResponse;
import com.ufape.agiota.negocio.frontage.Frontage;
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
    public ResponseEntity<CustomerResponse> cadastrarCliente(@Valid @RequestBody CustomerRequest cliente){
       CustomerResponse response = new CustomerResponse(frontage.saveCustomer(cliente.convertToEntity()));
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> buscarCliente(@PathVariable Long id){
        CustomerResponse response = new CustomerResponse(frontage.findCustomer(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<CustomerResponse>> buscarTodosClientes(){
        Iterable<CustomerResponse> response = frontage.findAllCustomers().stream().map(CustomerResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
