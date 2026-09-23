package com.necklogic.api.service;

import com.necklogic.api.dto.rating.MyRatingResponseDTO;
import com.necklogic.api.dto.rating.SubmitRatingRequestDTO;
import com.necklogic.api.exception.ForbiddenActionException;
import com.necklogic.api.model.Track;
import com.necklogic.api.model.TrackRating;
import com.necklogic.api.model.User;
import com.necklogic.api.repository.TrackRatingRepository;
import com.necklogic.api.repository.UserTrackEnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RatingService {

    private static final double MIN_COMPLETION_TO_RATE = 50.0;

    private final TrackService trackService;
    private final TrackRatingRepository ratingRepository;
    private final UserTrackEnrollmentRepository enrollmentRepository;

    public RatingService(TrackService trackService,
                         TrackRatingRepository ratingRepository,
                         UserTrackEnrollmentRepository enrollmentRepository) {
        this.trackService = trackService;
        this.ratingRepository = ratingRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public MyRatingResponseDTO getMyRating(Long trackId, User user) {
        Track track = trackService.getTrackOrThrow(trackId);
        boolean enrolled = enrollmentRepository.findByUserAndTrack(user, track).isPresent();
        double completion = enrolled ? trackService.completionPercentageFor(user, track) : 0.0;
        boolean canRate = enrolled && completion >= MIN_COMPLETION_TO_RATE;

        return ratingRepository.findByUserAndTrack(user, track)
                .map(rating -> new MyRatingResponseDTO(enrolled, completion, canRate, rating.getStars(), rating.getComment()))
                .orElseGet(() -> new MyRatingResponseDTO(enrolled, completion, canRate, null, null));
    }

    @Transactional
    public MyRatingResponseDTO submitRating(Long trackId, User user, SubmitRatingRequestDTO data) {
        Track track = trackService.getTrackOrThrow(trackId);

        boolean enrolled = enrollmentRepository.findByUserAndTrack(user, track).isPresent();
        if (!enrolled) {
            throw new ForbiddenActionException("Você precisa estar matriculado nesta trilha para avaliá-la.");
        }

        double completion = trackService.completionPercentageFor(user, track);
        if (completion < MIN_COMPLETION_TO_RATE) {
            throw new ForbiddenActionException("Conclua pelo menos 50% da trilha antes de avaliá-la.");
        }

        TrackRating rating = ratingRepository.findByUserAndTrack(user, track)
                .orElseGet(() -> new TrackRating(user, track, data.stars(), data.comment()));

        rating.setStars(data.stars());
        rating.setComment(data.comment());
        rating.setUpdatedAt(LocalDateTime.now());

        ratingRepository.save(rating);

        return new MyRatingResponseDTO(true, completion, true, rating.getStars(), rating.getComment());
    }
}