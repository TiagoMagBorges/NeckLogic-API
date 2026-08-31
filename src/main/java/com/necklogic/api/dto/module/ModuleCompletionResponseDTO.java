package com.necklogic.api.dto.module;

public record ModuleCompletionResponseDTO(
    Long moduleId,
    Integer xpGained,
    Integer totalXp,
    Integer level,
    Boolean leveledUp,
    Integer streak
) {}