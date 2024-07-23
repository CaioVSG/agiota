package com.ufape.agiota;

import com.ufape.agiota.negocio.enums.Frequency;
import com.ufape.agiota.negocio.models.Agiota;
import com.ufape.agiota.negocio.models.Borrowing;
import com.ufape.agiota.negocio.models.Customer;
import com.ufape.agiota.negocio.models.Installments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringBootApplication
public class AgiotaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgiotaApplication.class, args);

	}

}
