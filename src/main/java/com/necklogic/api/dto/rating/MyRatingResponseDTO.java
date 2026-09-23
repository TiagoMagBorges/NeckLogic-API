package com.necklogic.api.dto.rating;

public record MyRatingResponseDTO(
        boolean enrolled,
        double completionPercentage,
        boolean canRate,
        Integer stars,
        String comment
) {}