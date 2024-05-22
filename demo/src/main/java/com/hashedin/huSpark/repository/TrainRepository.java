package com.hashedin.huSpark.repository;

import com.hashedin.huSpark.model.Booking;
import com.hashedin.huSpark.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    boolean existsByName(String name);
    @Query("SELECT t FROM Train t JOIN t.routes r " +
            "WHERE r.departureTime BETWEEN :departureTimeFrom AND :departureTimeTo " +
            "ORDER BY r.departureTime")
    List<Train> findByRoutesDepartureTimeBetweenOrderByRoutesDepartureTime(
            @Param("departureTimeFrom") LocalDateTime departureTimeFrom,
            @Param("departureTimeTo") LocalDateTime departureTimeTo);

    @Query("SELECT t FROM Train t JOIN t.routes r1 JOIN t.routes r2 " +
            "WHERE r1.station.name = :boardingStationName " +
            "AND r2.station.name = :dropOffStationName")
    List<Train> findByRoutesBoardingStationNameAndRoutesDropOffStationName(
            @Param("boardingStationName") String boardingStationName,
            @Param("dropOffStationName") String dropOffStationName);

    @Query("SELECT t FROM Train t JOIN t.routes r1 JOIN t.routes r2 " +
            "WHERE (r1.station.name = :boardingStationName AND r1.departureTime BETWEEN :departureTimeFrom1 AND :departureTimeTo1) " +
            "OR (r2.station.name = :dropOffStationName AND r2.departureTime BETWEEN :departureTimeFrom2 AND :departureTimeTo2)")
    List<Train> findByRoutesBoardingStationNameAndRoutesDepartureTimeBetweenOrRoutesDropOffStationNameAndRoutesDepartureTimeBetween(
            @Param("boardingStationName") String boardingStationName,
            @Param("departureTimeFrom1") LocalDateTime departureTimeFrom1,
            @Param("departureTimeTo1") LocalDateTime departureTimeTo1,
            @Param("dropOffStationName") String dropOffStationName,
            @Param("departureTimeFrom2") LocalDateTime departureTimeFrom2,
            @Param("departureTimeTo2") LocalDateTime departureTimeTo2);
}
