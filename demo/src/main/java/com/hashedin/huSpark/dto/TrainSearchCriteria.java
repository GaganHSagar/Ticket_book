package com.hashedin.huSpark.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainSearchCriteria {
    private String boardingStationName;
    private String dropOffStationName;
    private LocalDateTime departureTimeFrom;
    private LocalDateTime departureTimeTo;
//    private Integer boardingStationDistanceFrom;
//    private Integer dropOffStationDistanceTo;
}
