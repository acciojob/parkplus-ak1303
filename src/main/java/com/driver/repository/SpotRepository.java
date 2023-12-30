package com.driver.repository;

import com.driver.Entity.Spot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer>{
    @Query(value = "select * from spot where parking_lot_id=:parkingLotId",nativeQuery = true)
    List<Spot> findSpotByParkingId(int parkingLotId);
}
