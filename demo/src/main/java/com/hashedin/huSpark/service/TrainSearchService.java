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
        // Implement search logic based on criteria
        String boardingStation =  criteria.getBoardingStationName();
        String dropOffStation =  criteria.getDropOffStationName();
        LocalDateTime departureTimeFrom = criteria.getDepartureTimeFrom();
        LocalDateTime departureTimeTo = criteria.getDepartureTimeTo();

        if (boardingStation == null || dropOffStation == null) {
            return ResponseEntity.badRequest().body("Boarding station or drop-off station should not be empty");
        }

        // Create a list of stations
        if (departureTimeFrom != null && departureTimeTo != null) {
            List<Train> trains = trainRepository.findByRoutesBoardingStationNameAndRoutesDepartureTimeBetweenOrRoutesDropOffStationNameAndRoutesDepartureTimeBetween(
                    boardingStation, departureTimeFrom, departureTimeTo,
                    dropOffStation, departureTimeFrom, departureTimeTo);
            return ResponseEntity.ok(trains);
        } else {
            List<Train> trains = trainRepository.findByRoutesBoardingStationNameAndRoutesDropOffStationName(
                    boardingStation, dropOffStation);
            return ResponseEntity.ok(trains);
        }
    }
}
