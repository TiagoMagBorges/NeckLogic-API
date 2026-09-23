package com.necklogic.api.service;

import com.necklogic.api.dto.user.UpdatePasswordRequestDTO;
import com.necklogic.api.dto.user.UpdateProfileRequestDTO;
import com.necklogic.api.model.Track;
import com.necklogic.api.model.User;
import com.necklogic.api.repository.TrackRatingRepository;
import com.necklogic.api.repository.TrackRepository;
import com.necklogic.api.repository.UserProgressRepository;
import com.necklogic.api.repository.UserRepository;
import com.necklogic.api.repository.UserTrackEnrollmentRepository;
import com.necklogic.api.repository.VerificationTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProgressRepository progressRepository;
    private final UserTrackEnrollmentRepository enrollmentRepository;
    private final TrackRepository trackRepository;
    private final TrackRatingRepository ratingRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserProgressRepository progressRepository,
                       UserTrackEnrollmentRepository enrollmentRepository,
                       TrackRepository trackRepository,
                       TrackRatingRepository ratingRepository,
                       VerificationTokenRepository tokenRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.progressRepository = progressRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.trackRepository = trackRepository;
        this.ratingRepository = ratingRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User updateProfile(User user, UpdateProfileRequestDTO dto) {
        if (dto.name() != null && !dto.name().isBlank()) {
            user.setName(dto.name());
        }

        if (dto.email() != null && !dto.email().isBlank() && !user.getEmail().equals(dto.email())) {
            if (userRepository.findByEmail(dto.email()) != null) {
                throw new IllegalArgumentException("E-mail já está em uso por outra conta.");
            }
            user.setEmail(dto.email());
        }

        return userRepository.save(user);
    }

    @Transactional
    public void updatePassword(User user, UpdatePasswordRequestDTO dto) {
        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("A senha atual está incorreta.");
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void deleteAccount(User user) {
        for (Track track : trackRepository.findByOwner(user)) {
            track.setOwner(null);
            trackRepository.save(track);
        }

        ratingRepository.deleteByUser(user);
        progressRepository.deleteByUser(user);
        enrollmentRepository.deleteByUser(user);
        tokenRepository.deleteByUser(user);
        userRepository.delete(user);
    }
}