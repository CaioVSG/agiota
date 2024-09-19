package com.ufape.agiota.negocio.service;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufape.agiota.dados.repository.AgiotaRepository;
import com.ufape.agiota.negocio.models.Agiota;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class AgiotaService implements AgiotaServiceInterface{
    final private AgiotaRepository agiotaRepository;

    @Transactional
    @Override
    public void delete(Long id, String idSession) {
        if (!find(id).getIdKc().equals(idSession)) throw new AccessDeniedException("User not allowed");
        agiotaRepository.deleteById(id);
    }

    @Override
    public Agiota find(Long id) {
        return agiotaRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override 
    public Agiota save(Agiota agiota) {
        return agiotaRepository.save(agiota);
    }

    @Override
    public List<Agiota> findAll() {
        return agiotaRepository.findAll();
    }

    @Transactional
    @Override
    public Agiota update(Agiota agiota, String idSession) {
        if (!agiota.getIdKc().equals(idSession)) throw new AccessDeniedException("User not allowed");
        return agiotaRepository.save(agiota);
    }


}
