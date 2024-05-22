package com.hashedin.huSpark.dto;

import com.hashedin.huSpark.model.Booking;
import com.hashedin.huSpark.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerRequest {

    private String name;

    private int age;

}

