package com.necklogic.api.dto.track;

public record PendingApprovalDTO(
        Long id,
        String title,
        String description,
        String ownerName
) {}