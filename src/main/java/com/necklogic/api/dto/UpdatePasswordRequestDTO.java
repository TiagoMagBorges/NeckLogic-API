package com.necklogic.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequestDTO(
    @NotBlank String currentPassword,
    @NotBlank @Size(min = 6) String newPassword
) {}