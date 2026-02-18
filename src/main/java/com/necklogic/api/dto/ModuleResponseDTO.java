package com.necklogic.api.dto;

import com.necklogic.api.model.enums.ModuleStatus;

public record ModuleResponseDTO(
    Long id,
    String title,
    Integer orderIndex,
    ModuleStatus status,
    Integer percentage,
    String sectionTitle,
    String sectionDescription
) {}