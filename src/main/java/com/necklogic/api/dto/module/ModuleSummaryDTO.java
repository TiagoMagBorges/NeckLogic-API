package com.necklogic.api.dto.module;

public record ModuleSummaryDTO(
        Long id,
        String title,
        Integer orderIndex
) {}