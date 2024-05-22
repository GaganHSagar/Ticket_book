package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.model.Passenger;
import com.hashedin.huSpark.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findByName(String name);
}
