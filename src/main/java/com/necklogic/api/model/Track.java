package com.necklogic.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tracks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "is_official", nullable = false)
    private boolean official = false;

    @Column(name = "is_published", nullable = false)
    private boolean published = false;

    @Column(name = "is_paid", nullable = false)
    private boolean paid = false;

    @Column(name = "price_cents")
    private Integer priceCents;

    @Column(name = "is_approved", nullable = false)
    private boolean approved = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Track(String title, String description, User owner, boolean official, boolean published) {
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.official = official;
        this.published = published;
        this.paid = false;
        this.createdAt = LocalDateTime.now();
    }
}