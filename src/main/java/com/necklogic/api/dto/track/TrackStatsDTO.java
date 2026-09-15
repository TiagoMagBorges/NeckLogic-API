package com.necklogic.api.dto.track;

import com.necklogic.api.dto.rating.RatingResponseDTO;

import java.util.List;

public record TrackStatsDTO(
        Long trackId,
        String title,
        boolean paid,
        Integer priceCents,
        int enrolledCount,
        int completedCount,
        double completionRate,
        Double averageRating,
        int ratingCount,
        long estimatedRevenueCents,
        List<RatingResponseDTO> ratings
) {}