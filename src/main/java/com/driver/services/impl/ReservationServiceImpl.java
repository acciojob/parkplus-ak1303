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
public class ReservationServiceImpl implements ReservationService {
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
        User user = userRepository3.findById(userId).orElse(null);
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).orElse(null);
        if(user==null || parkingLot==null)
//            throw new Exception("Cannot make reservation");
            return null;
        List<Spot> spotList = spotRepository3.findSpotByParkingId(parkingLotId);
        if(spotList.size()==0)//throw new Exception("Cannot make reservation");
            return null;
        int minAmount=Integer.MAX_VALUE;
        Spot reserveSpot = null;
        for(Spot spot: spotList){
            String spotType = spot.getSpotType().toString();
            int wheels = 0;
            if(spotType=="TWO_WHEELER")wheels=2;
            if(spotType=="FOUR_WHEELER")wheels=4;
            else wheels=100;
            if(wheels<numberOfWheels)continue;
            int perHourCost = spot.getPricePerHour();
            if(perHourCost<minAmount){
                minAmount=perHourCost;
                reserveSpot=spot;
            }
        }
        Reservation reservation = new Reservation();
        reservation.setSpot(reserveSpot);
        reservation.setUser(user);
        reservation.setNumberOfHours(timeInHours);
        reservationRepository3.save(reservation);
        return reservation;
    }
}
