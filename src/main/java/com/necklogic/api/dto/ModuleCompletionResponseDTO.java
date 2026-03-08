package com.necklogic.api.dto;

public record ModuleCompletionResponseDTO(
    Long moduleId,
    Integer xpGained,
    Integer totalXp,
    Integer currentLevel,
    boolean leveledUp
) {}