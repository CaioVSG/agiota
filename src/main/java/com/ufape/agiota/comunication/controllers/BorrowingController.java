package com.ufape.agiota.comunication.controllers;

import com.ufape.agiota.comunication.dto.borrowing.BorrowingRequest;
import com.ufape.agiota.comunication.dto.borrowing.BorrowingResponse;
import com.ufape.agiota.comunication.dto.payment.PaymentResponse;
import com.ufape.agiota.negocio.frontage.Frontage;
import com.ufape.agiota.negocio.models.Agiota;
import com.ufape.agiota.negocio.models.Customer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrowing") @RequiredArgsConstructor
public class BorrowingController {
    final private ModelMapper modelMapper;
    final private Frontage frontage;

    @PostMapping
    public ResponseEntity<BorrowingResponse> saveBorrowing(@Valid @RequestBody BorrowingRequest borrowing){
        Customer customer = frontage.findCustomer(borrowing.getCustomerId());
        Agiota agiota = frontage.findAgiota(borrowing.getAgiotaId());

        BorrowingResponse response = new BorrowingResponse(frontage.saveBorrowing(borrowing.convertToEntity(customer, agiota)));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingResponse> findBorrowing(@PathVariable Long id){
        BorrowingResponse response = new BorrowingResponse(frontage.findBorrowing(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/denied")
    public ResponseEntity<BorrowingResponse> deniedBorrowing(@PathVariable Long id){
        return new ResponseEntity<>(new BorrowingResponse(frontage.deniedBorrowing(id)), HttpStatus.OK);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<BorrowingResponse> acceptBorrowing(@PathVariable Long id){
        return new ResponseEntity<>(new BorrowingResponse(frontage.acceptBorrowing(id)), HttpStatus.OK);
    }

    @PostMapping("/{id}/installments/{installid}/pay")
    public ResponseEntity<PaymentResponse> payBorrowing(@PathVariable Long id, @PathVariable Long installid){
        return new ResponseEntity<>(new PaymentResponse(frontage.payBorrowing(id, installid)), HttpStatus.OK);
    }

    @PostMapping("/evaluateCustomer/{id}/{nota}")
    public ResponseEntity<BorrowingResponse> evaluateCustomerBorrowing(@PathVariable Long id, @PathVariable int nota){
        return new ResponseEntity<>( new BorrowingResponse(frontage.evaluateCustomerBorrowing(id,nota)),HttpStatus.OK);
    }

    @PostMapping("/evaluateAgiota/{id}/{nota}")
    public ResponseEntity<BorrowingResponse> evaluateAgiotaBorrowing(@PathVariable Long id, @PathVariable int nota){
        return new ResponseEntity<>( new BorrowingResponse(frontage.evaluateAgiotaBorrowing(id,nota)),HttpStatus.OK);
    }
}
