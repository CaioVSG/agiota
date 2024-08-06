package com.ufape.agiota.comunication.controllers;


import com.ufape.agiota.negocio.models.Agiota;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ufape.agiota.comunication.dto.agiota.AgiotaRequest;
import com.ufape.agiota.comunication.dto.agiota.AgiotaResponse;
import com.ufape.agiota.negocio.frontage.Frontage;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


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

    @PatchMapping("/agiota/{id}")
    public ResponseEntity<AgiotaResponse> updateAgiota(@PathVariable Long id, @Valid @RequestBody AgiotaRequest entity){
        Agiota agiota = frontage.findAgiota(id);
        modelMapper.map(entity, agiota);
        AgiotaResponse response = new AgiotaResponse(frontage.saveAgiota(agiota));
        return new ResponseEntity<>(response, HttpStatus.OK);
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

    @DeleteMapping("/agiota/{id}")
    public ResponseEntity<String> deleteAgiota(@PathVariable Long id){
        frontage.deleteAgiota(id);
        return new ResponseEntity<>("Agiota com ID " + id + " foi deletado", HttpStatus.NO_CONTENT);
    }

    
    
}
