package com.necklogic.api.dto;

public record UpdateSectionRequestDTO(
        String title,
        String description,
        Integer orderIndex
) {}
