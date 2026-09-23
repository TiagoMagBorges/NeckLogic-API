package com.necklogic.api.payment;

import com.necklogic.api.model.TrackPurchase;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MockPaymentGateway implements PaymentGateway {

    @Override
    public String getName() {
        return "MOCK";
    }

    @Override
    public CheckoutSession createCheckoutSession(TrackPurchase purchase) {
        String sessionId = "mock_" + UUID.randomUUID();
        return new CheckoutSession(sessionId, null);
    }
}