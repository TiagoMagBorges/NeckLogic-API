package com.necklogic.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSectionRequestDTO(
        @NotBlank String title,
        String description,
        @NotNull Integer orderIndex
) {}
