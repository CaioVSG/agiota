package com.ufape.agiota.negocio.service;

import com.ufape.agiota.dados.repository.RepositoryBorrowing;
import com.ufape.agiota.dados.repository.RepositoryPayment;
import com.ufape.agiota.negocio.enums.Status;
import com.ufape.agiota.negocio.models.Borrowing;
import com.ufape.agiota.negocio.models.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service @RequiredArgsConstructor
public class BorrowingService implements BorrowingServiceInterface{
    final private RepositoryBorrowing repositoryBorrowing;
    final private RepositoryPayment repositoryPayment;

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

    @Override
    public Payment pay(Long id, Long installid) {
        Borrowing borrowing = repositoryBorrowing.findById(id).orElse(null);
        Payment payment = new Payment();
        for (int i = 0; i < borrowing.getInstallmentsList().size(); i++) {
            if (Objects.equals(borrowing.getInstallmentsList().get(i).getId(), installid)) {
                  borrowing.getInstallmentsList().get(i).setStatus(true);
                  repositoryBorrowing.save(borrowing);
                  payment.setInstallments(borrowing.getInstallmentsList().get(i));
                  payment.setValue(borrowing.getInstallmentsList().get(i).getValue());
                  payment.setPaymenDate(borrowing.getInstallmentsList().get(i).getPaymentDate().getTime());
                  break;
            }
        }

        return  repositoryPayment.save(payment);
    }
}
