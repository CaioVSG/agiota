package com.ufape.agiota.comunication.dto.borrowing;

import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Borrowing;
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
    private Long userId;

    public Borrowing convertToEntity() {
        ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
        Borrowing obj = modelMapper.map(this, Borrowing.class);
        return obj;
    }

}
