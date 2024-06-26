package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByTrainId(Long trainId);
}
