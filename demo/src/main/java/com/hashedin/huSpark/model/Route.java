package com.hashedin.huSpark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Column
    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @Column
    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @Column
    @NotNull(message = "Distance from start is required")
    private Integer distanceFromStart;

    @JsonIgnore // Temporary measure to prevent infinite recursion
    public Train getTrain() {
        return train;
    }
}
