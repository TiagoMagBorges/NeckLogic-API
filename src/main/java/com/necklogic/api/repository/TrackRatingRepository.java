package com.necklogic.api.repository;

import com.necklogic.api.model.Track;
import com.necklogic.api.model.TrackRating;
import com.necklogic.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackRatingRepository extends JpaRepository<TrackRating, Long> {
    Optional<TrackRating> findByUserAndTrack(User user, Track track);
    List<TrackRating> findByTrackOrderByCreatedAtDesc(Track track);
    long countByTrack(Track track);
    void deleteByTrack(Track track);
    void deleteByUser(User user);
}