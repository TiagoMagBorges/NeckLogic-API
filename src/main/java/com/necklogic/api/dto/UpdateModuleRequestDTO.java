package com.necklogic.api.dto;

public record UpdateModuleRequestDTO(
        String title,
        Integer orderIndex,
        Integer xpReward,
        String content
) {}
