package com.hashedin.huSpark.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainSearchCriteria {
    private LocalDateTime departureTimeFrom;
    private LocalDateTime departureTimeTo;
    private String boardingStationName;
    private String dropOffStationName;

}
