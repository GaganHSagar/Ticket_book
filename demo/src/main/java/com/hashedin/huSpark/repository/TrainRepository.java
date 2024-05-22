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
            "AND r2.station.name = :dropOffStationName " +
            "ORDER BY r1.departureTime")
    List<Train> findByRoutesBoardingStationNameAndRoutesDropOffStationName(
            @Param("boardingStationName") String boardingStationName,
            @Param("dropOffStationName") String dropOffStationName);

    @Query("SELECT t FROM Train t JOIN t.routes r1 JOIN t.routes r2 " +
            "WHERE r1.station.name = :boardingStationName " +
            "AND r1.distanceFromStart BETWEEN :boardingDistanceFrom AND :boardingDistanceTo " +
            "AND r2.station.name = :dropOffStationName " +
            "AND r2.distanceFromStart BETWEEN :dropOffDistanceFrom AND :dropOffDistanceTo " +
            "AND (r1.departureTime BETWEEN :departureTimeFrom AND :departureTimeTo " +
            "OR r2.departureTime BETWEEN :departureTimeFrom AND :departureTimeTo) " +
            "ORDER BY r1.departureTime")
    List<Train> findByNearbyStationsAndDepartureTime(
            @Param("boardingStationName") String boardingStationName,
            @Param("boardingDistanceFrom") Integer boardingDistanceFrom,
            @Param("boardingDistanceTo") Integer boardingDistanceTo,
            @Param("dropOffStationName") String dropOffStationName,
            @Param("dropOffDistanceFrom") Integer dropOffDistanceFrom,
            @Param("dropOffDistanceTo") Integer dropOffDistanceTo,
            @Param("departureTimeFrom") LocalDateTime departureTimeFrom,
            @Param("departureTimeTo") LocalDateTime departureTimeTo);
}
