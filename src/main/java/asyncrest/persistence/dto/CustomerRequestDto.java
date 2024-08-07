package asyncrest.persistence.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * DTO for {@link asyncrest.persistence.Customer}
 */
public record CustomerRequestDto(
        @NotEmpty String fullName,
        @Email(message = "Invalid email address")
        @NotEmpty String email,
        @NotEmpty String gender) implements Serializable {
}