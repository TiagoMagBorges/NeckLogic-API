package com.necklogic.api.dto;

public record ModuleCompletionResponseDTO(
    Long moduleId,
    Integer xpGained,
    Integer totalXp,
    Integer level,
    Boolean leveledUp,
    Integer streak
) {}