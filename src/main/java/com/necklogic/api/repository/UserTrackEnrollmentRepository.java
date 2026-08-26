package com.necklogic.api.repository;

import com.necklogic.api.model.Track;
import com.necklogic.api.model.User;
import com.necklogic.api.model.UserTrackEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTrackEnrollmentRepository extends JpaRepository<UserTrackEnrollment, Long> {
    Optional<UserTrackEnrollment> findByUserAndTrack(User user, Track track);
    List<UserTrackEnrollment> findByUser(User user);
    void deleteByTrack(Track track);
}
