package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<ParkingLot> optionalParkingLot = parkingLotRepository1.findById(parkingLotId);
        if(!optionalParkingLot.isPresent())return null;
        ParkingLot parkingLot = optionalParkingLot.get();
        //Assign spot to given parking lot
        Spot newSpot = new Spot();
        newSpot.setParkingLot(parkingLot);//include this line as I have to return newSpot
        //Assign spot type on the basis of no of wheels
        if(numberOfWheels<4)
            newSpot.setSpotType(TWO_WHEELER);
        else if(numberOfWheels<5)
            newSpot.setSpotType(FOUR_WHEELER);
        else newSpot.setSpotType(OTHERS);
        newSpot.setPricePerHour(pricePerHour);
        newSpot.setOccupied(false);

        //change in parking lot for List<Spot>
        List<Spot> spotList = parkingLot.getSpotList();
        spotList.add(newSpot);

        parkingLotRepository1.save(parkingLot);

        return newSpot;
    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {

        Spot spot = null;
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        List<Spot> spotList = parkingLot.getSpotList();
        for(Spot spot1 : spotList){
            if(spot1.getId()==spotId){
                spot1.setPricePerHour(pricePerHour);
                spotRepository1.save(spot1);
                spot=spot1;
                break;
            }
        }
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
