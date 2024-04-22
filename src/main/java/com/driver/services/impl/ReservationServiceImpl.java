package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.model.User;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class
ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        if(!userRepository3.findById(userId).isPresent())throw new Exception("Cannot make reservation");
        User user = userRepository3.findById(userId).get();
        if(!parkingLotRepository3.findById(parkingLotId).isPresent())throw new Exception("Cannot make reservation");
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();

        List<Spot> spotList = parkingLot.getSpotList();
        int minAmount=Integer.MAX_VALUE;
        Spot reserveSpot = null;
        for(Spot spot: spotList){
            String spotType = spot.getSpotType().toString();
            int wheels = 0;
            if(spotType.equals("TWO_WHEELER"))wheels=2;
            else if(spotType.equals("FOUR_WHEELER"))wheels=4;
            else wheels=1000;
            if(wheels<numberOfWheels)continue;//available wheel space should be more than or equal to numberOfWheels
            int perHourCost = spot.getPricePerHour();
            if(spot.getOccupied()==false && perHourCost<minAmount){
                minAmount=perHourCost;
                reserveSpot=spot;
            }
        }
        if(reserveSpot==null)throw new Exception("Cannot make reservation");
        reserveSpot.setOccupied(true);

        Reservation reservation = new Reservation();
        reservation.setSpot(reserveSpot);
        reservation.setUser(user);
        reservation.setNumberOfHours(timeInHours);

        List<Reservation> userReservationList = user.getReservationList();
        List<Reservation> spotReservationList = reserveSpot.getReservationList();
        spotReservationList.add(reservation);
        userReservationList.add(reservation);
        userRepository3.save(user);
        spotRepository3.save(reserveSpot);
        return reservation;
    }
}
