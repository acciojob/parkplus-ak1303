package com.driver.services;

import com.driver.Entity.Payment;

public interface PaymentService {
    Payment pay(Integer reservationId, int amountSent, String mode) throws Exception;
}
