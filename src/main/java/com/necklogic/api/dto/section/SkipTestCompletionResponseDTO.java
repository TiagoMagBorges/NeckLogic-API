package com.necklogic.api.dto.section;

public record SkipTestCompletionResponseDTO(
        Boolean passed,
        Double scorePercentage,
        Integer xpGained,
        Integer totalXp,
        Integer level,
        Boolean leveledUp,
        Integer streak
) {}