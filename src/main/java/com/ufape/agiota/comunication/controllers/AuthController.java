package com.ufape.agiota.comunication.controllers;

import com.ufape.agiota.comunication.dto.Auth.TokenResponse;
import com.ufape.agiota.negocio.facade.Facade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    final private Facade facade;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestParam("username") String username, @RequestParam("password") String password) throws Exception {
        TokenResponse response = facade.login(username, password);
        return ResponseEntity.ok(response);
    }
}
