package com.ufape.agiota.comunication.controllers;

import com.ufape.agiota.comunication.dto.Avaliacao.AvaliacaoRequest;
import com.ufape.agiota.comunication.dto.borrowing.SaveBorrowingRequest;
import com.ufape.agiota.comunication.dto.borrowing.SaveBorrowingResponse;
import com.ufape.agiota.comunication.dto.borrowing.BorrowingRequest;
import com.ufape.agiota.comunication.dto.borrowing.BorrowingResponse;
import com.ufape.agiota.comunication.dto.installment.InstallmentResponse;
import com.ufape.agiota.comunication.dto.payment.PaymentResponse;
import com.ufape.agiota.negocio.facade.Facade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowing") @RequiredArgsConstructor
public class BorrowingController {
    final private ModelMapper modelMapper;
    final private Facade facade;

    @PostMapping
    public ResponseEntity<SaveBorrowingResponse> saveBorrowing(@Valid @RequestBody SaveBorrowingRequest borrowing){
        SaveBorrowingResponse response = new SaveBorrowingResponse(facade.saveBorrowing(borrowing.convertToEntity()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingResponse> findBorrowing(@PathVariable Long id){
        BorrowingResponse response = new BorrowingResponse(facade.findBorrowing(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/denied")
    public ResponseEntity<BorrowingResponse> deniedBorrowing(@PathVariable Long id){
        return new ResponseEntity<>(new BorrowingResponse(facade.deniedBorrowing(id)), HttpStatus.OK);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<BorrowingResponse> acceptBorrowing(@PathVariable Long id){
        return new ResponseEntity<>(new BorrowingResponse(facade.acceptBorrowing(id)), HttpStatus.OK);
    }

    @PostMapping("/{id}/request")
    public ResponseEntity<BorrowingResponse> requestBorrowing(@PathVariable Long id, @Valid @RequestBody BorrowingRequest borrowing){
        return new ResponseEntity<>(new BorrowingResponse(facade.requestBorrowing(id, borrowing)), HttpStatus.OK);
    }

    @PostMapping("/{id}/installments/{installid}/pay")
    public ResponseEntity<PaymentResponse> payBorrowing(@PathVariable Long id, @PathVariable Long installid){
        return new ResponseEntity<>(new PaymentResponse(facade.payBorrowing(id, installid)), HttpStatus.OK);
    }

    @GetMapping("/{id}/installments")
    public ResponseEntity<List<InstallmentResponse>> listInstallments(@PathVariable Long id){
        List<InstallmentResponse> response = facade.listInstallments(id).stream().map(InstallmentResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @PostMapping("/evaluateCustomer/{id}")
    public ResponseEntity<BorrowingResponse> evaluateCustomerBorrowing(@PathVariable Long id, @Valid @RequestBody AvaliacaoRequest avaliacao){
        return new ResponseEntity<>( new BorrowingResponse(facade.evaluateCustomerBorrowing(id,avaliacao.convertToEntity())),HttpStatus.OK);
    }

    @PostMapping("/evaluateAgiota/{id}")
    public ResponseEntity<BorrowingResponse> evaluateAgiotaBorrowing(@PathVariable Long id, @Valid @RequestBody AvaliacaoRequest avaliacao){
        return new ResponseEntity<>( new BorrowingResponse(facade.evaluateAgiotaBorrowing(id,avaliacao.convertToEntity())),HttpStatus.OK);
    }
}
