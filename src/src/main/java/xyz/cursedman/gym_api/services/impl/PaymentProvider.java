package xyz.cursedman.gym_api.services.impl;

import xyz.cursedman.gym_api.domain.entities.User;

public interface PaymentProvider {

	void doPayment(User user, Payment payable);


}
