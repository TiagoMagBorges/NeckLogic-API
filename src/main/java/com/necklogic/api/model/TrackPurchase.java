package com.necklogic.api.model;

import com.necklogic.api.model.enums.PurchaseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "track_purchases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    @Column(name = "amount_cents", nullable = false)
    private Integer amountCents;

    @Column(nullable = false)
    private String gateway;

    @Column(name = "session_id", nullable = false, unique = true)
    private String sessionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PurchaseStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    public TrackPurchase(User user, Track track, Integer amountCents, String gateway, String sessionId) {
        this.user = user;
        this.track = track;
        this.amountCents = amountCents;
        this.gateway = gateway;
        this.sessionId = sessionId;
        this.status = PurchaseStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }
}