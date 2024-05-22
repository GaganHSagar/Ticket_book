package com.hashedin.huSpark.service;

import com.hashedin.huSpark.model.Route;
import com.hashedin.huSpark.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private StationService stationService;

    public Route findOrCreateRoute(Route route) {
        // Ensure the station is saved or retrieved if it already exists
//        route.setStation(stationService.findOrCreateStation(route.getStation()));
        return routeRepository.save(route);
    }

    public List<Route> saveRoutes(List<Route> routes) {
        for (Route route : routes) {
            findOrCreateRoute(route);
        }
        return routes;
    }
}
