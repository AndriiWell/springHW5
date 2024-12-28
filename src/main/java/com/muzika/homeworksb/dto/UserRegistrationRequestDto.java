package com.muzika.homeworksb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequestDto(
        @NotBlank @Size(min = 4, max = 50) String email,
        @NotBlank @Size(min = 1, max = 100) String password,
        @NotBlank @Size(min = 1, max = 100) String repeatPassword
) {
}
