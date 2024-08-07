package asyncrest.persistence.dto;

import asyncrest.persistence.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link Customer}
 */
public record CustomerResponseDto(
        @NotNull @Positive Long id,
        @NotBlank String fullName,
        @Email(message = "Invalid email address") @NotBlank String email,
        String gender,
        @Past @PastOrPresent LocalDateTime created,
        @PastOrPresent LocalDateTime lastUpdated) implements Serializable {
}