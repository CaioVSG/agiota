package com.ufape.agiota.comunication.controllers;


import com.ufape.agiota.comunication.dto.agiota.AgiotaUpdateRequest;
import com.ufape.agiota.negocio.models.Agiota;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.ufape.agiota.comunication.dto.agiota.AgiotaRequest;
import com.ufape.agiota.comunication.dto.agiota.AgiotaResponse;
import com.ufape.agiota.negocio.facade.Facade;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequestMapping("/agiota")
@RestController
@RequiredArgsConstructor
public class AgiotaController {
    final private ModelMapper modelMapper;
    final private Facade facade;

    @PostMapping("/register")
    public ResponseEntity<AgiotaResponse> saveCustomer(@Valid @RequestBody AgiotaRequest agiota) throws AccessDeniedException{
        AgiotaResponse response = new AgiotaResponse(facade.saveAgiota(agiota.convertToEntity(), agiota.getPassword()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AgiotaResponse> updateAgiota(@PathVariable Long id, @Valid @RequestBody AgiotaUpdateRequest entity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        Agiota agiota = facade.findAgiota(id);
        modelMapper.map(entity, agiota);
        AgiotaResponse response = new AgiotaResponse(facade.updateAgiota(agiota, principal.getSubject()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgiotaResponse> findAgiota(@PathVariable Long id){
        AgiotaResponse response = new AgiotaResponse(facade.findAgiota(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/agiotas")
    public ResponseEntity<Iterable<AgiotaResponse>> findAllAgiotas(){
        Iterable<AgiotaResponse> response = facade.findAllAgiotas().stream().map(AgiotaResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgiota(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        facade.deleteAgiota(id, principal.getSubject());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
    
}
