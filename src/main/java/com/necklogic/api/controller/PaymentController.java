package com.necklogic.api.controller;

import com.necklogic.api.dto.payment.ConfirmPaymentRequestDTO;
import com.necklogic.api.dto.payment.PurchaseResponseDTO;
import com.necklogic.api.model.User;
import com.necklogic.api.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/{sessionId}/confirm")
    public ResponseEntity<PurchaseResponseDTO> confirm(
            @PathVariable String sessionId,
            @AuthenticationPrincipal User user,
            @RequestBody @Valid ConfirmPaymentRequestDTO data) {
        return ResponseEntity.ok(paymentService.confirm(sessionId, user, data.outcome()));
    }
}