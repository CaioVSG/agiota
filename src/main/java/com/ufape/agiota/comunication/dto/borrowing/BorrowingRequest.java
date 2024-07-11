package com.ufape.agiota.comunication.dto.borrowing;

import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.enums.Status;
import com.ufape.agiota.negocio.models.Agiota;
import com.ufape.agiota.negocio.models.Borrowing;
import com.ufape.agiota.negocio.models.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class BorrowingRequest {
    private Double fees;
    private BigDecimal value;
    private Integer numberInstallments;
    private Date initialDate;
    private Date payday;
    private Double discount;
    private Long customerId;
    private Long agiotaId;

    public Borrowing convertToEntity(Customer customer, Agiota agiota) {
        ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
        Borrowing obj = modelMapper.map(this, Borrowing.class);
        obj.setCustomer(customer);
        obj.setAgiota(agiota);
        obj.setStatus(Status.SOLICITADO);
        return obj;
    }

}
