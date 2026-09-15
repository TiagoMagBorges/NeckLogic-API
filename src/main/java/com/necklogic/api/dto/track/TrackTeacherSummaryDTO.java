package com.necklogic.api.dto.track;

public record TrackTeacherSummaryDTO(
        Long id,
        String title,
        String description,
        String ownerName,
        boolean official,
        boolean published,
        boolean paid,
        Integer priceCents,
        int enrolledCount,
        int completedCount,
        double completionRate,
        Double averageRating,
        int ratingCount,
        long estimatedRevenueCents
) {}