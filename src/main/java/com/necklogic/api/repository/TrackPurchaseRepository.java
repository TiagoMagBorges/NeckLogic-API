package com.necklogic.api.repository;

import com.necklogic.api.model.Track;
import com.necklogic.api.model.TrackPurchase;
import com.necklogic.api.model.enums.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackPurchaseRepository extends JpaRepository<TrackPurchase, Long> {
    Optional<TrackPurchase> findBySessionId(String sessionId);
    List<TrackPurchase> findByTrackAndStatus(Track track, PurchaseStatus status);
}