package com.ufape.agiota.comunication.dto.borrowing;

import com.ufape.agiota.comunication.dto.customer.CustomerRequest;
import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Borrowing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingResponse {
    private Long id;
    private Double fees;
    private BigDecimal value;
    private Integer numberInstallments;
    private Date initialDate;
    private Date payday;
    private Double discount;
    private CustomerRequest customerRequest;

    public BorrowingResponse(Borrowing obj) {
        if (obj == null) throw new IllegalArgumentException("Objeto não pode ser nulo");
        else{
            ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
            modelMapper.map(obj, this);
        }
    }
}
