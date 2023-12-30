package com.driver.model;

import com.driver.model.PaymentMode;
import com.driver.model.Reservation;

import javax.persistence.*;
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private boolean paymentCompleted;
    @Enumerated
    PaymentMode paymentMode;
    @OneToOne
    @JoinColumn
    Reservation reservation;

    public Payment(int id, boolean paymentCompleted, PaymentMode paymentMode, Reservation reservation) {
        this.id = id;
        this.paymentCompleted = paymentCompleted;
        this.paymentMode = paymentMode;
        this.reservation = reservation;
    }

    public Payment() {
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentCompleted=" + paymentCompleted +
                ", paymentMode=" + paymentMode +
                ", reservation=" + reservation +
                '}';
    }
}
