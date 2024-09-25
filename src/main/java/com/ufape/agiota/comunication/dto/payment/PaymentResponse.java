package com.ufape.agiota.comunication.dto.payment;

import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class PaymentResponse {
    private Long id;
    private Date paymentDate;
    private BigDecimal value;

    public PaymentResponse(Payment obj) {
        if (obj == null) throw new IllegalArgumentException("Objeto não pode ser nulo");
        else{
            ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
            modelMapper.map(obj, this);
        }
    }
}
