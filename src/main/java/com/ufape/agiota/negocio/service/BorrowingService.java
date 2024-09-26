package com.ufape.agiota.negocio.service;


import com.ufape.agiota.dados.repository.BorrowingRepository;
import com.ufape.agiota.dados.repository.PaymentRepository;
import com.ufape.agiota.negocio.enums.Avaliado;
import com.ufape.agiota.negocio.enums.Frequency;
import com.ufape.agiota.negocio.enums.Status;
import com.ufape.agiota.negocio.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service @RequiredArgsConstructor
public class BorrowingService implements BorrowingServiceInterface{
    final private BorrowingRepository borrowingRepository;
    final private PaymentRepository paymentRepository;

    @Transactional
    @Override
    public Borrowing save(Borrowing borrowing) {
        return borrowingRepository.save(borrowing); }

    @Override
    public void delete(Long id) { borrowingRepository.deleteById(id); }

    @Override
    public Borrowing find(Long id) { return borrowingRepository.findById(id).orElse(null); }

    @Override
    public Borrowing denied(Long id) {
        Borrowing borrowing = borrowingRepository.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
        borrowing.setStatus(Status.RECUSADO);
        return  borrowingRepository.save(borrowing);
    }

    @Override
    public Borrowing accept(Long id) {
        Borrowing borrowing = borrowingRepository.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
        borrowing.setInitialDate(new GregorianCalendar());
        borrowing.setStatus(Status.ANDAMENTO);
        borrowing.setDiscount(0.0);
        borrowing.setInstallmentsList(gerarParcelas(borrowing));
        return  borrowingRepository.save(borrowing);
    }

    @Override
    public Borrowing request(Long id, Customer customer){
        Borrowing borrowing = borrowingRepository.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
        borrowing.setCustomer(customer);
        borrowing.setStatus(Status.SOLICITADO);
        return borrowingRepository.save(borrowing);
    }


    @Override
    public Borrowing evaluateCustomerBorrowing(Long id, Avaliacao avaliacao, String sessionId) {
        Borrowing borrowing = borrowingRepository.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
        avaliacao.setAvaliado(Avaliado.CLIENTE);
        if (!borrowing.getAgiota().getIdKc().equals(sessionId)){
            throw new AccessDeniedException("Você não tem permissão para avaliar este emprestimo");
        }
        for(Avaliacao temp: borrowing.getListaAvaliacoes()){
            if (temp.getAvaliado() == Avaliado.CLIENTE){
                throw new AvaliacaoDuplicadaException("O agiota já contem uma avaliação.");
            }
        }
        borrowing.addAvaliacoes(avaliacao);
        return borrowingRepository.save(borrowing);
    }

    @Override
    public Borrowing evaluateAgiotaBorrowing(Long id, Avaliacao avaliacao, String sessionId) {
        Borrowing borrowing = borrowingRepository.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
        avaliacao.setAvaliado(Avaliado.AGIOTA);
        if (!borrowing.getCustomer().getIdKc().equals(sessionId)){
            throw new AccessDeniedException("Você não tem permissão para avaliar este emprestimo");
        }
        for(Avaliacao temp: borrowing.getListaAvaliacoes()){
            if (temp.getAvaliado() == Avaliado.AGIOTA){
                throw new AvaliacaoDuplicadaException("O agiota já contem uma avaliação.");
            }
        }
        borrowing.addAvaliacoes(avaliacao);
        return borrowingRepository.save(borrowing);
    }

    @Override
    public List<Installments> listInstallments(Long id){
        Borrowing borrowing = borrowingRepository.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
        return borrowing.getInstallmentsList();
    }

    @Override
    public List<Borrowing> findAvailable(){
        return borrowingRepository.findByStatus(Status.DISPONIVEL);
    }

    @Override
    public List<Borrowing> findAgiotaBorrowings(Long id){
        return borrowingRepository.findByAgiotaId(id);
    }

    @Override
    public List<Borrowing> findCustomerBorrowings(Long id){
        return borrowingRepository.findByCustomerId(id);
    }

    public Payment pay(Long id, Long installid, String sessionId) {
        Borrowing borrowing = borrowingRepository.findById(id).orElseThrow(() -> new RuntimeException("Emprestimo não encontrado"));
        Payment payment = new Payment();
        if (!borrowing.getCustomer().getIdKc().equals(sessionId)){
            throw new AccessDeniedException("Você não tem permissão para pagar este emprestimo");
        }
        for (int i = 0; i < borrowing.getInstallmentsList().size(); i++) {
            if (Objects.equals(borrowing.getInstallmentsList().get(i).getId(), installid)) {
                  borrowing.getInstallmentsList().get(i).setStatus(true);
                  boolean allPaid = borrowing.getInstallmentsList().stream().allMatch(Installments::getStatus);
                  if (allPaid) {
                      borrowing.setStatus(Status.CONCLUIDO);
                  }
                  borrowingRepository.save(borrowing);
                  payment.setInstallments(borrowing.getInstallmentsList().get(i));
                  payment.setValue(borrowing.getInstallmentsList().get(i).getValue());
                  payment.setPaymenDate(borrowing.getInstallmentsList().get(i).getPaymentDate().getTime());
                  break;
            }
        }

        return  paymentRepository.save(payment);
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
