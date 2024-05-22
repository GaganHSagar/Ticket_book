package com.hashedin.huSpark.controller;

import com.hashedin.huSpark.dto.TrainSearchCriteria;
import com.hashedin.huSpark.model.Train;
import com.hashedin.huSpark.service.TrainService;
import com.hashedin.huSpark.service.TrainSearchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/trains")
public class TrainController {
    @Autowired
    private TrainService trainService;

    @Autowired
    private TrainSearchService trainSearchService;

    @GetMapping("/search")
    public ResponseEntity<?> searchTrains(@RequestBody TrainSearchCriteria criteria){
        ResponseEntity<?> responseEntity = trainSearchService.searchTrains(criteria);
        if (responseEntity.getBody() != null) {
            return ResponseEntity.ok(responseEntity.getBody());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addTrain(@RequestBody Train train) {
        ResponseEntity<?> responseEntity = trainService.addTrain(train);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    @GetMapping
    public ResponseEntity<?> getAllTrains() {
        ResponseEntity<?> responseEntity = trainService.getAllTrains();
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Train> getTrainById(@PathVariable Long id) {
        ResponseEntity<Train> responseEntity = trainService.getTrainById(id);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainById(@PathVariable Long id) {
        ResponseEntity<Void> responseEntity = trainService.deleteTrainById(id);
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }
}
