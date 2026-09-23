package com.necklogic.api.dto.rating;

import java.time.LocalDateTime;

public record RatingResponseDTO(
        Long id,
        Integer stars,
        String comment,
        String userName,
        LocalDateTime createdAt
) {}