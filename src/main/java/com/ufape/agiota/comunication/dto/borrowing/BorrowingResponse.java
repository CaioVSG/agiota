package com.ufape.agiota.comunication.dto.borrowing;

import com.ufape.agiota.comunication.dto.agiota.AgiotaResponse;
import com.ufape.agiota.comunication.dto.customer.CustomerResponse;
import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.enums.Status;
import com.ufape.agiota.negocio.models.Avaliacao;
import com.ufape.agiota.negocio.models.Borrowing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BorrowingResponse {
    private Long id;
    private Double fees;
    private BigDecimal value;
    private Integer numberInstallments;
    private Date initialDate;
    private int payday;
    private Double discount;
    private Status status;
    private AgiotaResponse agiota;
    private CustomerResponse customer;
    List<Avaliacao> listaAvaliacoes;

    public BorrowingResponse(Borrowing obj) {
        if (obj == null) throw new IllegalArgumentException("Objeto não pode ser nulo");
        else{
            ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
            modelMapper.map(obj, this);
        }
    }
}
