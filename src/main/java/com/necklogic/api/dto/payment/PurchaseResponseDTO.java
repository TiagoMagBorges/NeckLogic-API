package com.necklogic.api.dto.payment;

import com.necklogic.api.model.enums.PurchaseStatus;

import java.time.LocalDateTime;

public record PurchaseResponseDTO(
        Long purchaseId,
        String sessionId,
        PurchaseStatus status,
        Integer amountCents,
        String trackTitle,
        LocalDateTime confirmedAt
) {}