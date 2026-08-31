package com.necklogic.api.dto.section;

public record UpdateSectionRequestDTO(
        String title,
        String description,
        Integer orderIndex
) {}
