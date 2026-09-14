package com.necklogic.api.dto.module;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateModuleRequestDTO(
        @NotBlank String title,
        @NotNull Integer orderIndex,
        Integer xpReward,
        String content,
        Boolean isSkipTest
) {}