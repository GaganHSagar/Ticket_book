package com.hashedin.huSpark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    @Column
    @NotNull
    private LocalDate bookingDate;

    @Column
    @NotNull
    private LocalDate travelDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SeatType seatType;
}
