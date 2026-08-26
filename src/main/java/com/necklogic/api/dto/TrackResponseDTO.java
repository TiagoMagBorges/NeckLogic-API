package com.necklogic.api.dto;

public record TrackResponseDTO(
        Long id,
        String title,
        String description,
        String ownerName,
        boolean official,
        boolean published,
        boolean paid,
        Integer priceCents
) {}
