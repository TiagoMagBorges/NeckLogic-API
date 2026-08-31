package com.necklogic.api.dto.section;

import com.necklogic.api.dto.module.ModuleSummaryDTO;

import java.util.List;

public record SectionResponseDTO(
        Long id,
        String title,
        String description,
        Integer orderIndex,
        List<ModuleSummaryDTO> modules
) {}