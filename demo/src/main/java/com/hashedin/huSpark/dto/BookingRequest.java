package com.hashedin.huSpark.dto;

import com.hashedin.huSpark.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookingRequest {

    @Getter
    private Long userId;

    @Getter
    private Long trainId;

    private Station boardingStation;

    private Station dropOffStation;

    private LocalDate travelDate;

    private SeatType seatType;

    private List<PassengerRequest> passengers;

}