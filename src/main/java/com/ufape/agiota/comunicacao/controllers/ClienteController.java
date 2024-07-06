package com.ufape.agiota.comunicacao.controllers;

import com.ufape.agiota.comunicacao.dto.clientes.ClientesRequest;
import com.ufape.agiota.comunicacao.dto.clientes.ClientesResponse;
import com.ufape.agiota.negocio.fachada.Fachada;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes") @RequiredArgsConstructor
public class ClienteController {
    final private ModelMapper modelMapper;
    final private Fachada fachada;

    @PostMapping
    public ResponseEntity<ClientesResponse> cadastrarCliente( @Valid @RequestBody ClientesRequest cliente){
       ClientesResponse response = new ClientesResponse(fachada.salvarCliente(cliente.convertToEntity()));
       return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientesResponse> buscarCliente(@PathVariable Long id){
        ClientesResponse response = new ClientesResponse(fachada.buscarCliente(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<ClientesResponse>> buscarTodosClientes(){
        Iterable<ClientesResponse> response = fachada.buscarTodosClientes().stream().map(ClientesResponse::new).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
