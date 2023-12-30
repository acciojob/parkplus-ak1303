package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.exceptions.parkingLotDoesNotExistException;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.driver.model.SpotType.*;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setAddress(address);
        parkingLot.setName(name);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot newSpot = new Spot();
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).orElse(null);
        if(parkingLot==null){
            throw new parkingLotDoesNotExistException("Parking lot does not exist with id you have provided");
        }
        //Assign spot to given parking lot
        newSpot.setParkingLot(parkingLot);
        //Assign spot type on the basis of no of wheels
        SpotType spotType;
        if(numberOfWheels==2)
            spotType=TWO_WHEELER;
        else if(numberOfWheels==4)
            spotType=FOUR_WHEELER;
        else spotType=OTHERS;
        newSpot.setSpotType(spotType);
        newSpot.setPricePerHour(pricePerHour);
        newSpot.setOccupied(false);
        spotRepository1.save(newSpot);
        return newSpot;
    }

    @Override
    public void deleteSpot(int spotId) {
        Spot spot = spotRepository1.findById(spotId).orElse(null);
        if(spot!=null){
            spotRepository1.delete(spot);
        }
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        Spot spot = spotRepository1.findById(spotId).orElse(null);
        if(spot.getParkingLot().getId()==parkingLotId)
            spot.setPricePerHour(pricePerHour);
        spotRepository1.save(spot);
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).orElse(null);
        if(parkingLot!=null){
            parkingLotRepository1.delete(parkingLot);
        }
    }
}
