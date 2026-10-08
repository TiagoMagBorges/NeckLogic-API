package com.necklogic.api.service;

import com.necklogic.api.dto.payment.CheckoutResponseDTO;
import com.necklogic.api.dto.payment.PurchaseResponseDTO;
import com.necklogic.api.exception.ForbiddenActionException;
import com.necklogic.api.exception.ResourceNotFoundException;
import com.necklogic.api.model.enums.PurchaseStatus;
import com.necklogic.api.model.Track;
import com.necklogic.api.model.TrackPurchase;
import com.necklogic.api.model.User;
import com.necklogic.api.payment.CheckoutSession;
import com.necklogic.api.payment.PaymentGateway;
import com.necklogic.api.repository.TrackPurchaseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final TrackPurchaseRepository purchaseRepository;
    private final TrackService trackService;
    private final PaymentGateway paymentGateway;

    public PaymentService(TrackPurchaseRepository purchaseRepository, TrackService trackService, PaymentGateway paymentGateway) {
        this.purchaseRepository = purchaseRepository;
        this.trackService = trackService;
        this.paymentGateway = paymentGateway;
    }

    @Transactional
    public CheckoutResponseDTO createCheckout(Long trackId, User user) {
        Track track = trackService.getTrackOrThrow(trackId);

        if (!track.isPublished()) {
            throw new ForbiddenActionException("Esta trilha ainda não foi publicada.");
        }
        if (!track.isPaid()) {
            throw new ForbiddenActionException("Esta trilha não é paga.");
        }

        Integer amountCents = track.getPriceCents() != null ? track.getPriceCents() : 0;

        TrackPurchase purchase = new TrackPurchase(user, track, amountCents, paymentGateway.getName(), null);
        CheckoutSession session = paymentGateway.createCheckoutSession(purchase);
        purchase.setSessionId(session.sessionId());
        purchase = purchaseRepository.save(purchase);

        return new CheckoutResponseDTO(
                purchase.getId(),
                purchase.getSessionId(),
                purchase.getGateway(),
                purchase.getAmountCents(),
                track.getTitle(),
                session.redirectUrl()
        );
    }

    @Transactional
    public PurchaseResponseDTO confirm(String sessionId, User user, PurchaseStatus outcome) {
        if (outcome == PurchaseStatus.PENDING) {
            throw new ForbiddenActionException("Resultado de pagamento inválido.");
        }

        TrackPurchase purchase = purchaseRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Sessão de pagamento não encontrada."));

        if (!purchase.getUser().getId().equals(user.getId())) {
            throw new ForbiddenActionException("Você não tem permissão para confirmar este pagamento.");
        }

        if (purchase.getStatus() == PurchaseStatus.PENDING) {
            purchase.setStatus(outcome);
            purchase.setConfirmedAt(LocalDateTime.now());
            purchase = purchaseRepository.save(purchase);

            if (outcome == PurchaseStatus.PAID) {
                trackService.grantPaidEnrollment(user, purchase.getTrack());
            }
        }

        return new PurchaseResponseDTO(
                purchase.getId(),
                purchase.getSessionId(),
                purchase.getStatus(),
                purchase.getAmountCents(),
                purchase.getTrack().getTitle(),
                purchase.getConfirmedAt()
        );
    }
}