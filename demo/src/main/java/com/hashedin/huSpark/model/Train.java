package com.hashedin.huSpark.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Train name is required")
    private String name;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    private List<Route> routes = new ArrayList<>();

    @ElementCollection
    private Set<DayOfWeek> runningDays;

    @Column
    private Integer class_3ASeats = 70;

    @Column
    private Integer class_2ASeats = 70;

    @Column
    private Integer class_SleeperSeats = 70;

    @Column
    private Integer class_1ASeats = 70;

    public Integer getAvailableSeats(SeatType seatType) {
        return switch (seatType) {
            case CLASS_SL -> this.class_SleeperSeats;
            case CLASS_3A -> this.class_3ASeats;
            case CLASS_2A -> this.class_2ASeats;
            case CLASS_1A ->
                    this.class_1ASeats; // Assuming no seats for AC 1 Tier for now
            default -> 0;
        };
    }
}
