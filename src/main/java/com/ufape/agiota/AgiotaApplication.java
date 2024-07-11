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

		Agiota agiota = new Agiota();
		agiota.setFees(10.0);
		agiota.setName("Jonas");

		Customer cliente = new Customer();
		cliente.setName("Dany");
		BigDecimal valor = new BigDecimal(2000.00);
		Borrowing emprestimo = new Borrowing(valor,6, 15, Frequency.MENSAL, cliente, agiota);
		emprestimo.aceitar();

		List<Installments> parcelas = emprestimo.getInstallmentsList();
		BigDecimal acumulado = new BigDecimal(0);
		for(int i = 1; i <= parcelas.size(); i++ ) {
			System.out.println("-------Parcela " + i + "-------");
			acumulado = acumulado.add(parcelas.get(i-1).getValue());
			System.out.println("Valor: R$" + parcelas.get(i-1).getValue());
			SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
			System.out.println("Data: " + dataFormatada.format(parcelas.get(i-1).getPaymentDate().getTimeInMillis()));
			System.out.println("Status pagamento: " + parcelas.get(i-1).getStatus());
		}
		System.out.println("Valor total: R$" + acumulado);
	}

}
