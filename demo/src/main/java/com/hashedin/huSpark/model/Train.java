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
    private int class_3ASeats = 70;

    @Column
    private int class_2ASeats = 70;

    @Column
    private int class_SleeperSeats = 70;

    @Column
    private int class_GeneralSeats = 70;


}
