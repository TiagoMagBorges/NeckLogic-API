package com.necklogic.api.dto.module;

public record ModuleDetailDTO(
        Long id,
        String title,
        Integer orderIndex,
        Integer xpReward,
        String content,
        Long sectionId,
        Boolean isSkipTest
) {}