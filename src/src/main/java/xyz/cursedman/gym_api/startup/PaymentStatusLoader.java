package xyz.cursedman.gym_api.startup;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
import xyz.cursedman.gym_api.services.PaymentStatusService;

@Component
@AllArgsConstructor
public class PaymentStatusLoader implements CommandLineRunner {

	PaymentStatusService paymentStatusService;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Loading Payment Statuses");

		for (PaymentStatusEnum val : PaymentStatusEnum.values()) {
			paymentStatusService.createPaymentStatus(val.name());
		}
	}
}
