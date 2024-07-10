package com.ufape.agiota.comunication.dto.borrowing;

import com.ufape.agiota.comunication.dto.customer.CustomerRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

}
