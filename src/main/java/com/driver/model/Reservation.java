package com.driver.model;

import javax.persistence.*;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int noOfHours;
    @ManyToOne
    User user;
    @ManyToOne
    Spot spot;
    @OneToOne
    Payment payment;

    public Reservation(int id, int noOfHours, User user, Spot spot, Payment payment) {
        this.id = id;
        this.noOfHours = noOfHours;
        this.user = user;
        this.spot = spot;
        this.payment = payment;
    }

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNoOfHours() {
        return noOfHours;
    }

    public void setNoOfHours(int noOfHours) {
        this.noOfHours = noOfHours;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", noOfHours=" + noOfHours +
                ", user=" + user +
                ", spot=" + spot +
                ", paymentMode=" + payment +
                '}';
    }
}

