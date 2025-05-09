package xyz.cursedman.gym_api.domain.stripe;

import com.stripe.model.Price;
import com.stripe.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisteredStripeProduct {
	private Product product;
	private Price price;
}
