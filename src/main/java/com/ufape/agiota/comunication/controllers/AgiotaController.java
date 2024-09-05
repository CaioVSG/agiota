package com.ufape.agiota.comunication.controllers;


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


@RestController
@RequiredArgsConstructor
public class AgiotaController {
    final private ModelMapper modelMapper;
    final private Facade facade;

    @PostMapping("/agiota")
    public ResponseEntity<AgiotaResponse> saveAgiota(@Valid @RequestBody AgiotaRequest agiota){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        if (facade.checkDuplicatedUser(principal.getSubject())){throw new AccessDeniedException("User already exists");}
        AgiotaResponse response = new AgiotaResponse(facade.saveAgiota(agiota.convertToEntity()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/agiota/{id}")
    public ResponseEntity<AgiotaResponse> updateAgiota(@PathVariable Long id, @Valid @RequestBody AgiotaRequest entity){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        Agiota agiota = facade.findAgiota(id);
        if(!principal.getSubject().equals(agiota.getIdKc())){throw new AccessDeniedException("User not allowed");}
        modelMapper.map(entity, agiota);
        AgiotaResponse response = new AgiotaResponse(facade.saveAgiota(agiota));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/agiota/{id}")
    public ResponseEntity<AgiotaResponse> findAgiota(@PathVariable Long id){
        AgiotaResponse response = new AgiotaResponse(facade.findAgiota(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/agiotas")
    public ResponseEntity<Iterable<AgiotaResponse>> findAllAgiotas(){
        Iterable<AgiotaResponse> response = facade.findAllAgiotas().stream().map(AgiotaResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/agiota/{id}")
    public ResponseEntity<String> deleteAgiota(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt principal = (Jwt) authentication.getPrincipal();
        if(!principal.getSubject().equals(facade.findAgiota(id).getIdKc())){throw new AccessDeniedException("User not allowed");}
        facade.deleteAgiota(id);
        return new ResponseEntity<>("Agiota com ID " + id + " foi deletado", HttpStatus.NO_CONTENT);
    }

    
    
}
