package com.necklogic.api.dto.payment;

import com.necklogic.api.model.enums.PurchaseStatus;
import jakarta.validation.constraints.NotNull;

public record ConfirmPaymentRequestDTO(
        @NotNull PurchaseStatus outcome
) {}