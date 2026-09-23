package com.necklogic.api.dto.payment;

public record CheckoutResponseDTO(
        Long purchaseId,
        String sessionId,
        String gateway,
        Integer amountCents,
        String trackTitle,
        String redirectUrl
) {}