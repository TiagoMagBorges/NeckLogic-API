package com.necklogic.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTrackRequestDTO(
        @NotBlank String title,
        String description,
        Boolean paid,
        Integer priceCents
) {}
