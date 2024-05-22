package com.hashedin.huSpark.service;

import com.hashedin.huSpark.model.Station;
import com.hashedin.huSpark.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public Station findOrCreateStation(Station station) {
        Optional<Station> existingStation = stationRepository.findByName(station.getName());
        if (existingStation.isPresent()) {
            return existingStation.get();
        } else {
            return stationRepository.save(station);
        }
    }
}
