package com.ufape.agiota.negocio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufape.agiota.dados.repository.RepositoryAgiota;
import com.ufape.agiota.negocio.models.Agiota;
import java.util.List;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class AgiotaService implements AgiotaServiceInterface{
    final private RepositoryAgiota agiotaRepository;

    @Override
    public void delete(Long id) {
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

    
}
