//package xyz.cursedman.gym_api.services;
//
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import xyz.cursedman.gym_api.domain.entities.Payment;
//import xyz.cursedman.gym_api.domain.entities.PaymentStatusEnum;
//import xyz.cursedman.gym_api.mappers.PaymentMapper;
//
//import java.net.URI;
//import java.util.UUID;
//
/// **
// * Class that uses abstracted away paymentProvider for payments and handles coordination
// * between business logic and implementations
// *
// * @see PaymentService
// * @see PaymentProvider
// */
//@Service
//@AllArgsConstructor
//public class PaymentCoordinator {
//
//	private final PaymentProvider paymentProvider;
//
//	private final PaymentMapper paymentMapper;
//
//	public URI getPaymentUri(Payment payment) {
//		return paymentProvider.getPaymentUri(paymentMapper.toPaymentDto(payment));
//	}
//}
