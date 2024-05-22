package com.hashedin.huSpark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "train_id")
        private Train train;

        @ManyToOne
        @JoinColumn(name = "boarding_station_id")
        @NotNull(message = "Boarding station is required")
        private Station boardingStation;

        @ManyToOne
        @JoinColumn(name = "dropoff_station_id")
        @NotNull(message = "Drop-off station is required")
        private Station dropOffStation;

        @Column
        @NotNull(message = "Booking date is required")
        private LocalDate bookingDate;

        @Column
        @NotNull(message = "Travel date is required")
        private LocalDate travelDate;

        @Enumerated(EnumType.STRING)
        @NotNull(message = "Seat type is required")
        private SeatType seatType;

        // List of passengers
        @ManyToOne
        @JoinColumn(name = "booking_id")
        private Booking booking;

        @Column
        private boolean bookingStatus;
}
