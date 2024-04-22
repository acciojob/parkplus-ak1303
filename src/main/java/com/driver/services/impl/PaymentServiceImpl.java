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
        Reservation reservation = reservationRepository2.findById(reservationId).get();
        int perHourCost = reservation.getSpot().getPricePerHour();
        int duration = reservation.getNumberOfHours();
        int bill = duration*perHourCost;
        if(amountSent<bill) throw new Exception("Insufficient Amount");
        mode = mode.toUpperCase();

        Payment payment = new Payment();
        if(mode.equals("CASH")){
            payment.setPaymentMode(PaymentMode.CASH);
        }else if(mode.equals("UPI")){
            payment.setPaymentMode(PaymentMode.UPI);
        } else if (mode.equals("CARD")) {
            payment.setPaymentMode(PaymentMode.CARD);
        }else {
            throw new Exception("Payment mode not detected");
        }
        payment.setPaymentCompleted(true);

        reservation.getSpot().setOccupied(false);
        reservation.setPayment(payment);
        payment.setReservation(reservation);

        reservationRepository2.save(reservation);

        return payment;
    }
}
