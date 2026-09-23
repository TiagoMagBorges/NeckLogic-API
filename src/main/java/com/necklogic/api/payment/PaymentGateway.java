package com.necklogic.api.payment;

import com.necklogic.api.model.TrackPurchase;


public interface PaymentGateway {
    String getName();

    CheckoutSession createCheckoutSession(TrackPurchase purchase);
}