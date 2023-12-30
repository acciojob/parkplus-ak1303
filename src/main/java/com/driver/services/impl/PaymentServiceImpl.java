package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.Reservation;
import com.driver.model.PaymentMode;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        Reservation reservation = reservationRepository2.findById(reservationId).orElse(null);
        int perHourCost = reservation.getSpot().getPricePerHour();
        int duration = reservation.getNoOfHours();
        int bill = duration*perHourCost;
        if(amountSent<bill) throw new Exception("Insufficient Amount");
        mode = mode.toUpperCase();
        if(!mode.equals("CASH") && !mode.equals("UPI") && !mode.equals("CARD"))
            throw new Exception("Payment mode not detected");
        Payment payment = new Payment();
        payment.setPaymentCompleted(true);
        payment.setPaymentMode(PaymentMode.valueOf(mode));
        payment.setReservation(reservation);
        paymentRepository2.save(payment);
        return payment;
    }
}
