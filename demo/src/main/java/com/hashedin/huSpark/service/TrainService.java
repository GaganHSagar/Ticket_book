package com.hashedin.huSpark.service;

import com.hashedin.huSpark.exception.ResourceNotFoundException;
import com.hashedin.huSpark.model.Route;
import com.hashedin.huSpark.model.Station;
import com.hashedin.huSpark.model.Train;
import com.hashedin.huSpark.repository.StationRepository;
import com.hashedin.huSpark.repository.TrainRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {
    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationService stationService;

//    @Transactional
    public ResponseEntity<?> addTrain(Train train) {
        if (trainRepository.existsByName(train.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Train name must be unique");
        }
        try {
            // Save related stations through StationService
            for (Route route : train.getRoutes()) {
                // Ensure each route has a reference to the train
                route.setTrain(train);

                // Ensure each route has a reference to the station
                Station station = route.getStation();
                Station existingStation = stationService.findOrCreateStation(station);
                route.setStation(existingStation);
            }

            // Save the train
            Train savedTrain = trainRepository.save(train);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTrain);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
//        try {
//            // Save related stations through StationService
////            Train savedTrain = trainRepository.save(train);
//            System.out.println("train service thing done");
//
////            for (Route route : train.getRoutes()) {
////                stationService.findOrCreateStation(route.getStation());
////            }
//            // Save the train
//            // Save related routes through RouteService
////            for (Route route : train.getRoutes()) {
////                route.setTrain(savedTrain);
////                routeService.findOrCreateRoute(route);
////                System.out.println("route service thing done");
////            }
//
//            return ResponseEntity.status(HttpStatus.CREATED).body(savedTrain);
//        } catch (DataIntegrityViolationException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data integrity violation: " + e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
//        }
    }

    public ResponseEntity<List<Train>> getAllTrains() {
        List<Train> trains = trainRepository.findAll();
        return ResponseEntity.ok(trains);
    }

    public ResponseEntity<Train> getTrainById(Long id) {
        Train train = trainRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Train not found with id: " + id));
        return ResponseEntity.ok(train);
    }

    @Transactional
    public ResponseEntity<Void> deleteTrainById(Long id) {
        if (!trainRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        trainRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
