package com.necklogic.api.repository;

import com.necklogic.api.model.TrackPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrackPurchaseRepository extends JpaRepository<TrackPurchase, Long> {
    Optional<TrackPurchase> findBySessionId(String sessionId);
}