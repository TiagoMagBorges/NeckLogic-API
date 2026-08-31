package com.necklogic.api.dto.track;

public record UpdateTrackRequestDTO(
        String title,
        String description,
        Boolean published,
        Boolean paid,
        Integer priceCents
) {}
