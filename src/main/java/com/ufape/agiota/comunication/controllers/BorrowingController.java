package com.ufape.agiota.comunication.controllers;

import com.ufape.agiota.comunication.dto.borrowing.BorrowingRequest;
import com.ufape.agiota.comunication.dto.borrowing.BorrowingResponse;
import com.ufape.agiota.negocio.frontage.Frontage;
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
    public ResponseEntity<BorrowingResponse> saveBorrowing(@Valid @RequestBody BorrowingRequest cliente){
        BorrowingResponse response = new BorrowingResponse(frontage.saveBorrowing(cliente.convertToEntity()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowingResponse> findBorrowing(@PathVariable Long id){
        BorrowingResponse response = new BorrowingResponse(frontage.findBorrowing(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
