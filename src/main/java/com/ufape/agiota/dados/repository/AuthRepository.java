package com.ufape.agiota.dados.repository;

import com.ufape.agiota.negocio.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdKc(String idKc);
}