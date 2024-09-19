package com.ufape.agiota.negocio.service;

import com.ufape.agiota.dados.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;




    public boolean checkDuplicatedUser(String id) {
        return authRepository.findByIdKc(id).isPresent();
    }
}
