package com.hashedin.huSpark.service;

import java.time.LocalDateTime;
import java.util.List;

import com.hashedin.huSpark.dto.TrainSearchCriteria;
import com.hashedin.huSpark.model.Train;
import com.hashedin.huSpark.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TrainSearchService {
    @Autowired
    private TrainRepository trainRepository;

    public ResponseEntity<?> searchTrains(TrainSearchCriteria criteria) {
        // Implementing the  search logic based on criteria of a user



        // Creating a list of stations
        String boardingStation = criteria.getBoardingStationName();
        String dropOffStation = criteria.getDropOffStationName();
        LocalDateTime departureTimeFrom = criteria.getDepartureTimeFrom();
        LocalDateTime departureTimeTo = criteria.getDepartureTimeTo();
//        Integer boardingStationDistanceFrom = criteria.getBoardingStationDistanceFrom();
//        Integer dropOffStationDistanceTo = criteria.getDropOffStationDistanceTo();

        if (boardingStation == null || dropOffStation == null) {
            return ResponseEntity.badRequest().body("Boarding station or drop-off station should not be empty");
        }

        if (departureTimeFrom != null && departureTimeTo != null) {
            List<Train> trains = trainRepository.findByNearbyStationsAndDepartureTime(
                    boardingStation, 5, 5,
                    dropOffStation, 5, 5,
                    departureTimeFrom, departureTimeTo);
            return ResponseEntity.ok(trains);
        } else {
            List<Train> trains = trainRepository.findByRoutesBoardingStationNameAndRoutesDropOffStationName(
                    boardingStation, dropOffStation);
            return ResponseEntity.ok(trains);
        }
    }
}
