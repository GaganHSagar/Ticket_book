package com.hashedin.huSpark.model;

import com.hashedin.huSpark.constants.ApplicationConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = ApplicationConstants.NAME_VALIDATION_MESSAGE)
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank(message = ApplicationConstants.EMAIL_VALIDATION_MESSAGE)
    @Email(message = ApplicationConstants.EMAIL_VALIDATION_MESSAGE)
    private String email;

    @NotBlank(message = ApplicationConstants.PASSWORD_VALIDATION_MESSAGE)
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = ApplicationConstants.USER_ROLE_VALIDATION_MESSAGE)
    private UserRole userRole;

    @NotNull(message = ApplicationConstants.DATE_OF_BIRTH_VALIDATION_MESSAGE)
    private LocalDate birthDay;

    @Column(nullable = false) // Not null constraint
    @Pattern(regexp = "^[\\d]{10}$", message = "Phone number must be 10 digits") // Regular expression for validation
    private String phoneNumber;
}
