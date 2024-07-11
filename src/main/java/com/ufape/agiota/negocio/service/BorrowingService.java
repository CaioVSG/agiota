package com.ufape.agiota.negocio.service;

import com.ufape.agiota.dados.repository.RepositoryBorrowing;
import com.ufape.agiota.negocio.enums.Status;
import com.ufape.agiota.negocio.models.Borrowing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class BorrowingService implements BorrowingServiceInterface{
    final private RepositoryBorrowing repositoryBorrowing;

    @Transactional
    @Override
    public Borrowing save(Borrowing borrowing) { return repositoryBorrowing.save(borrowing); }

    @Override
    public void delete(Long id) { repositoryBorrowing.deleteById(id); }

    @Override
    public Borrowing find(Long id) { return repositoryBorrowing.findById(id).orElse(null); }

    @Override
    public Borrowing denied(Long id) {
        Borrowing borrowing = repositoryBorrowing.findById(id).orElse(null);

        borrowing.setStatus(Status.RECUSADO);
        return  repositoryBorrowing.save(borrowing);
    }

    @Override
    public Borrowing accept(Long id) {
        Borrowing borrowing = repositoryBorrowing.findById(id).orElse(null);

        borrowing.aceitar();
        return  repositoryBorrowing.save(borrowing);
    }
}
