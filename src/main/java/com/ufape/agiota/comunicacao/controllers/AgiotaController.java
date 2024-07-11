package com.ufape.agiota.comunicacao.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufape.agiota.comunicacao.dto.agiota.AgiotaRequest;
import com.ufape.agiota.comunicacao.dto.agiota.AgiotaResponse;
import com.ufape.agiota.negocio.frontage.Frontage;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
public class AgiotaController {
    final private ModelMapper modelMapper;
    final private Frontage frontage;

    @PostMapping("/agiota")
    public ResponseEntity<AgiotaResponse> saveAgiota(@Valid @RequestBody AgiotaRequest agiota){
        AgiotaResponse response = new AgiotaResponse(frontage.saveAgiota(agiota.convertToEntity()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/agiota/{id}")
    public ResponseEntity<AgiotaResponse> findAgiota(@PathVariable Long id){
        AgiotaResponse response = new AgiotaResponse(frontage.findAgiota(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/agiotas")
    public ResponseEntity<Iterable<AgiotaResponse>> findAllAgiotas(){
        Iterable<AgiotaResponse> response = frontage.findAllAgiotas().stream().map(AgiotaResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
