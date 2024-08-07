package com.ufape.agiota.comunication.dto.borrowing;

import com.ufape.agiota.config.SpringApplicationContext;
import com.ufape.agiota.negocio.enums.Status;
import com.ufape.agiota.negocio.models.Borrowing;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Calendar;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SaveBorrowingRequest {
    @NotNull(message = "Informar o valor do Juros é obrigatório")
    private Double fees;

    @NotNull(message = "Informar o valor do empréstimo é obrigatório")
    private BigDecimal value;

    @NotNull(message = "Informar a quantidade de parcelas é obrigatório")
    private Integer numberInstallments;

    @NotNull(message = "Informar a data de inicio do empréstimo é obrigatório")
    private Calendar initialDate;

    @NotNull(message = "Informar a data de pagamento é obrigatório")
    private int payday;

    private Double discount;

    private Long agiotaId;

    public Borrowing convertToEntity() {
        ModelMapper modelMapper = (ModelMapper) SpringApplicationContext.getBean("modelMapper");
        Borrowing borrowing =  modelMapper.map(this, Borrowing.class);
        borrowing.setStatus(Status.DISPONIVEL);
        return borrowing;
    }

}
