package com.ufape.agiota.negocio.service;

import com.ufape.agiota.dados.repository.RepositoryBorrowing;
import com.ufape.agiota.negocio.models.Borrowing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class BorrowingService implements BorrowingServiceInterface{
    final private RepositoryBorrowing repositoryBorrowing;

    @Transactional
    @Override
    public Borrowing save(Borrowing customer) { return repositoryBorrowing.save(customer); }

    @Override
    public void delete(Long id) { repositoryBorrowing.deleteById(id); }

    @Override
    public Borrowing find(Long id) { return repositoryBorrowing.findById(id).orElse(null); }

}
