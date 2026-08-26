package com.necklogic.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateModuleRequestDTO(
        @NotBlank String title,
        @NotNull Integer orderIndex,
        Integer xpReward,
        String content
) {}
