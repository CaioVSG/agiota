package com.ufape.agiota.negocio.service;

import com.ufape.agiota.dados.repository.RepositoryAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RepositoryAuth repositoryAuth;




    public boolean checkDuplicatedUser(String id) {
        return repositoryAuth.findByIdKc(id).isPresent();
    }
}
