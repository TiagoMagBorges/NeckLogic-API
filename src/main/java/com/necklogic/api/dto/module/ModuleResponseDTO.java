package com.necklogic.api.dto.module;

import com.necklogic.api.model.enums.ModuleStatus;

public record ModuleResponseDTO(
    Long id,
    String title,
    Integer orderIndex,
    ModuleStatus status,
    Integer percentage,
    Long sectionId,
    String sectionTitle,
    String sectionDescription
) {}