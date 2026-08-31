package com.necklogic.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_track_enrollments", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "track_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTrackEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    @Column(nullable = false)
    private Integer xp = 0;

    @Column(name = "user_level", nullable = false)
    private Integer level = 1;

    @Column(name = "current_streak", nullable = false)
    private Integer currentStreak = 0;

    @Column(name = "last_activity_date")
    private LocalDate lastActivityDate;

    @Column(name = "enrolled_at", nullable = false)
    private LocalDateTime enrolledAt = LocalDateTime.now();

    public UserTrackEnrollment(User user, Track track) {
        this.user = user;
        this.track = track;
        this.xp = 0;
        this.level = 1;
        this.currentStreak = 0;
        this.enrolledAt = LocalDateTime.now();
    }

    public void addXp(Integer gainedXp) {
        this.xp += gainedXp;
        this.level = calculateLevel(this.xp);
    }

    private Integer calculateLevel(Integer totalXp) {
        return (int) (0.1 * Math.sqrt(totalXp)) + 1;
    }
}