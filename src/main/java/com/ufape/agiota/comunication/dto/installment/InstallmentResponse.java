package com.ufape.agiota.comunication.dto.installment;

import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.models.Installments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Calendar;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class InstallmentResponse {
    private Long id;
    private Calendar paymentDate;
    private Boolean status;
    private BigDecimal value;


    public InstallmentResponse(Installments obj){
        if(obj == null) throw new IllegalArgumentException("Objeto não pode ser nulo");
        else{
            ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
            modelMapper.map(obj, this);
        }

    }
}
