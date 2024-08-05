package com.ufape.agiota.negocio.service;

import com.ufape.agiota.dados.repository.RepositoryBorrowing;
import com.ufape.agiota.dados.repository.RepositoryPayment;
import com.ufape.agiota.negocio.enums.Avaliado;
import com.ufape.agiota.negocio.enums.Frequency;
import com.ufape.agiota.negocio.enums.Status;
import com.ufape.agiota.negocio.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

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
        Borrowing borrowing = repositoryBorrowing.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));

        borrowing.setStatus(Status.RECUSADO);
        return  repositoryBorrowing.save(borrowing);
    }

    @Override
    public Borrowing accept(Long id) {
        Borrowing borrowing = repositoryBorrowing.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
        borrowing.setInitialDate(new GregorianCalendar());
        borrowing.setStatus(Status.ANDAMENTO);
        borrowing.setDiscount(0.0);
        borrowing.setInstallmentsList(gerarParcelas(borrowing));
        return  repositoryBorrowing.save(borrowing);
    }



    //Em ambos métodos o objeto Avaliação não é salvo no banco de dados, logo, a avaliação não é persistida.
    @Override
    public Borrowing evaluateCustomerBorrowing(Long id, int nota) {
        Borrowing borrowing = repositoryBorrowing.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
        Avaliacao avaliacao = null;

        for(Avaliacao temp: borrowing.getListaAvaliacoes()){
            if (temp.getAvaliado() == Avaliado.CLIENTE){
                avaliacao = temp;
            }
        }
        if (avaliacao == null){
            avaliacao = new Avaliacao();
            avaliacao.setAvaliado(Avaliado.CLIENTE);
        }else{
            throw new AvaliacaoDuplicadaException("O cliente já contem uma avaliação.");
        }

        return repositoryBorrowing.save(borrowing);
    }

    @Override
    public Borrowing evaluateAgiotaBorrowing(Long id, int nota) {
        Borrowing borrowing = repositoryBorrowing.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
        Avaliacao avaliacao = null;
        for(Avaliacao temp: borrowing.getListaAvaliacoes()){
            if (temp.getAvaliado() == Avaliado.AGIATA){
                avaliacao = temp;
            }
        }
        if (avaliacao == null){
            avaliacao = new Avaliacao();
            avaliacao.setAvaliado(Avaliado.AGIATA);
        }else{
            throw new AvaliacaoDuplicadaException("O agiota já contem uma avaliação.");
        }
        return repositoryBorrowing.save(borrowing);
    }

    @Override
    public List<Installments> listInstallments(Long id){
        Borrowing borrowing = repositoryBorrowing.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
        return borrowing.getInstallmentsList();
    }



    public Payment pay(Long id, Long installid) {
        Borrowing borrowing = repositoryBorrowing.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
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



    private List<Installments> gerarParcelas(Borrowing borrowing){

        double jurusDia = borrowing.getFees()/100/30;
        BigDecimal valorParcela = borrowing.getValue().divide(new BigDecimal(borrowing.getNumberInstallments()), RoundingMode.UP);
        List<Installments> parcelas = new ArrayList<>();
        Calendar dataPagamento = (GregorianCalendar) borrowing.getInitialDate().clone();
        dataPagamento.set(Calendar.DAY_OF_MONTH, borrowing.getPayday());
        long hoje = new Date().getTime();
        for(int i=1; i <= borrowing.getNumberInstallments(); i++){
            Installments parcela = new Installments();
            parcela.setStatus(false);
            GregorianCalendar temp = (GregorianCalendar) dataPagamento.clone();
            if(borrowing.getFrequency() == Frequency.MENSAL){
                temp.add(Calendar.MONTH, i);
                parcela.setPaymentDate(temp);
            }else if(borrowing.getFrequency() == Frequency.QUINZENAL){
                temp.add(Calendar.DATE, 15*i);
                parcela.setPaymentDate(temp);
            }else{
                temp.add(Calendar.WEEK_OF_YEAR, i);
                parcela.setPaymentDate(temp);
            }

            long proximoPagament = parcela.getPaymentDate().getTimeInMillis();
            long l = proximoPagament - hoje;
            hoje = proximoPagament;
            int dias = Math.round(l /86400000f);
            valorParcela = valorParcela.multiply(new BigDecimal(1.0+jurusDia*dias));
            parcela.setValue(valorParcela);
            parcelas.add(parcela);

        }
        return parcelas;

    }
}
