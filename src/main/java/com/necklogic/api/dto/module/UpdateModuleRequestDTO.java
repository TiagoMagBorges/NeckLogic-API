package com.necklogic.api.dto.module;

public record UpdateModuleRequestDTO(
        String title,
        Integer orderIndex,
        Integer xpReward,
        String content
) {}
