package com.necklogic.api.service;

import com.necklogic.api.dto.rating.RatingResponseDTO;
import com.necklogic.api.dto.track.TrackStatsDTO;
import com.necklogic.api.dto.track.TrackTeacherSummaryDTO;
import com.necklogic.api.model.Track;
import com.necklogic.api.model.TrackRating;
import com.necklogic.api.model.User;
import com.necklogic.api.model.UserTrackEnrollment;
import com.necklogic.api.model.enums.PurchaseStatus;
import com.necklogic.api.repository.TrackPurchaseRepository;
import com.necklogic.api.repository.TrackRatingRepository;
import com.necklogic.api.repository.UserTrackEnrollmentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TrackStatsService {

    private final TrackService trackService;
    private final UserTrackEnrollmentRepository enrollmentRepository;
    private final TrackRatingRepository ratingRepository;
    private final TrackPurchaseRepository purchaseRepository;

    public TrackStatsService(TrackService trackService,
                             UserTrackEnrollmentRepository enrollmentRepository,
                             TrackRatingRepository ratingRepository,
                             TrackPurchaseRepository purchaseRepository) {
        this.trackService = trackService;
        this.enrollmentRepository = enrollmentRepository;
        this.ratingRepository = ratingRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public TrackStatsDTO getStats(Long trackId, User user) {
        Track track = trackService.getTrackOrThrow(trackId);
        trackService.requireEditAccess(user, track);

        Aggregates stats = aggregate(track);
        List<RatingResponseDTO> ratings = stats.ratings().stream()
                .map(this::toRatingDTO)
                .toList();

        return new TrackStatsDTO(
                track.getId(),
                track.getTitle(),
                track.isPaid(),
                track.getPriceCents(),
                stats.enrolledCount(),
                stats.completedCount(),
                stats.completionRate(),
                stats.averageRating(),
                stats.ratingCount(),
                stats.revenueCents(),
                ratings
        );
    }

    public List<TrackTeacherSummaryDTO> summarize(List<Track> tracks) {
        return tracks.stream().map(this::toSummaryDTO).toList();
    }

    private TrackTeacherSummaryDTO toSummaryDTO(Track track) {
        Aggregates stats = aggregate(track);

        return new TrackTeacherSummaryDTO(
                track.getId(),
                track.getTitle(),
                track.getDescription(),
                track.getOwner() != null ? track.getOwner().getName() : "NeckLogic",
                track.isOfficial(),
                track.isPublished(),
                track.isApproved(),
                track.isPaid(),
                track.getPriceCents(),
                stats.enrolledCount(),
                stats.completedCount(),
                stats.completionRate(),
                stats.averageRating(),
                stats.ratingCount(),
                stats.revenueCents()
        );
    }

    private Aggregates aggregate(Track track) {
        List<UserTrackEnrollment> enrollments = enrollmentRepository.findByTrack(track);
        List<TrackRating> ratings = ratingRepository.findByTrackOrderByCreatedAtDesc(track);

        int enrolledCount = enrollments.size();
        int completedCount = (int) enrollments.stream()
                .filter(enrollment -> trackService.completionPercentageFor(enrollment.getUser(), track) >= 100.0)
                .count();
        double completionRate = enrolledCount > 0 ? (completedCount * 100.0) / enrolledCount : 0.0;

        Double averageRating = ratings.isEmpty()
                ? null
                : ratings.stream().mapToInt(TrackRating::getStars).average().orElse(0.0);

        long revenueCents = track.isPaid()
                ? purchaseRepository.findByTrackAndStatus(track, PurchaseStatus.PAID).stream()
                .mapToLong(purchase -> purchase.getAmountCents())
                .sum()
                : 0L;

        return new Aggregates(enrolledCount, completedCount, completionRate, averageRating, ratings.size(), revenueCents, ratings);
    }

    private RatingResponseDTO toRatingDTO(TrackRating rating) {
        return new RatingResponseDTO(rating.getId(), rating.getStars(), rating.getComment(), rating.getUser().getName(), rating.getCreatedAt());
    }

    private record Aggregates(int enrolledCount, int completedCount, double completionRate,
                              Double averageRating, int ratingCount, long revenueCents,
                              List<TrackRating> ratings) {}
}