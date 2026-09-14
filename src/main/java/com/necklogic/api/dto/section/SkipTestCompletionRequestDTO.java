package com.necklogic.api.dto.section;

public record SkipTestCompletionRequestDTO(
        Double mistakesCount,
        Integer drillCount
) {}