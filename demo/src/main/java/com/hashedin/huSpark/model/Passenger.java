package com.hashedin.huSpark.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Passenger name is required")
    private String name;

    @NotNull(message = "Passenger age is required")
    private int age;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Flag to indicate if passenger is on waiting list
    @Column
    private boolean bookingStatus;
}
