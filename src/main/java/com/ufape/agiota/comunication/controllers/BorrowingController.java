package com.ufape.agiota.comunication.controllers;

import com.ufape.agiota.comunication.dto.Avaliacao.AvaliacaoRequest;
import com.ufape.agiota.comunication.dto.borrowing.BorrowingRequest;
import com.ufape.agiota.comunication.dto.borrowing.SaveBorrowingResponse;
import com.ufape.agiota.comunication.dto.borrowing.BorrowingResponse;
import com.ufape.agiota.comunication.dto.installment.InstallmentResponse;
import com.ufape.agiota.comunication.dto.payment.PaymentResponse;
import com.ufape.agiota.negocio.facade.Facade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrowing") @RequiredArgsConstructor
public class BorrowingController {
    final private Facade facade;

    @PreAuthorize("hasAnyRole('AGIOTA', 'ADMINISTRADOR')")
    @PostMapping
    public ResponseEntity<SaveBorrowingResponse> saveBorrowing(@Valid @RequestBody BorrowingRequest borrowing){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        SaveBorrowingResponse response = new SaveBorrowingResponse(facade.saveBorrowing(borrowing.convertToEntity(), principal.getSubject()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BorrowingResponse>> listBorrowings(){
        List<BorrowingResponse> response = facade.findAvailable().stream().map(BorrowingResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('AGIOTA')")
    @GetMapping("/agiota")
    public ResponseEntity<List<BorrowingResponse>> listAgiotaBorrowings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        List<BorrowingResponse> response = facade.findAgiotaBorrowings(principal.getSubject()).stream().map(BorrowingResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/customer")
    public ResponseEntity<List<BorrowingResponse>> listCustomerBorrowings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        List<BorrowingResponse> response = facade.findCustomerBorrowings(principal.getSubject()).stream().map(BorrowingResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<BorrowingResponse> findBorrowing(@PathVariable Long id){
        BorrowingResponse response = new BorrowingResponse(facade.findBorrowing(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('AGIOTA', 'ADMINISTRADOR')")
    @PostMapping("/{id}/denied")
    public ResponseEntity<BorrowingResponse> deniedBorrowing(@PathVariable Long id){
        return new ResponseEntity<>(new BorrowingResponse(facade.deniedBorrowing(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('AGIOTA', 'ADMINISTRADOR')")
    @PostMapping("/{id}/accept")
    public ResponseEntity<BorrowingResponse> acceptBorrowing(@PathVariable Long id){
        return new ResponseEntity<>(new BorrowingResponse(facade.acceptBorrowing(id)), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMINISTRADOR')")
    @PostMapping("/{id}/request")
    public ResponseEntity<BorrowingResponse> requestBorrowing(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        return new ResponseEntity<>(new BorrowingResponse(facade.requestBorrowing(id, principal.getSubject())), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMINISTRADOR')")
    @PostMapping("/{id}/installments/{installid}/pay")
    public ResponseEntity<PaymentResponse> payBorrowing(@PathVariable Long id, @PathVariable Long installid){
        return new ResponseEntity<>(new PaymentResponse(facade.payBorrowing(id, installid)), HttpStatus.OK);
    }


    @GetMapping("/{id}/installments")
    public ResponseEntity<List<InstallmentResponse>> listInstallments(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();

        if (!principal.getSubject().equals(facade.findBorrowing(id).getCustomer().getIdKc())){
            throw new AccessDeniedException("Você não tem permissão para acessar este recurso");
        }
        List<InstallmentResponse> response = facade.listInstallments(id).stream().map(InstallmentResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('AGIOTA', 'ADMINISTRADOR')")
    @PostMapping("/evaluateCustomer/{id}")
    public ResponseEntity<BorrowingResponse> evaluateCustomerBorrowing(@PathVariable Long id, @Valid @RequestBody AvaliacaoRequest avaliacao){
        return new ResponseEntity<>( new BorrowingResponse(facade.evaluateCustomerBorrowing(id,avaliacao.convertToEntity())),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMINISTRADOR')")
    @PostMapping("/evaluateAgiota/{id}")
    public ResponseEntity<BorrowingResponse> evaluateAgiotaBorrowing(@PathVariable Long id, @Valid @RequestBody AvaliacaoRequest avaliacao){
        return new ResponseEntity<>( new BorrowingResponse(facade.evaluateAgiotaBorrowing(id,avaliacao.convertToEntity())),HttpStatus.OK);
    }
}
