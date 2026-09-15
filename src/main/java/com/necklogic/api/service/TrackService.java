package com.necklogic.api.service;

import com.necklogic.api.dto.rating.RatingResponseDTO;
import com.necklogic.api.dto.track.CreateTrackRequestDTO;
import com.necklogic.api.dto.track.TrackResponseDTO;
import com.necklogic.api.dto.track.TrackStatsDTO;
import com.necklogic.api.dto.track.TrackTeacherSummaryDTO;
import com.necklogic.api.dto.track.UpdateTrackRequestDTO;
import com.necklogic.api.exception.ForbiddenActionException;
import com.necklogic.api.exception.ResourceNotFoundException;
import com.necklogic.api.model.Module;
import com.necklogic.api.model.Section;
import com.necklogic.api.model.Track;
import com.necklogic.api.model.TrackRating;
import com.necklogic.api.model.User;
import com.necklogic.api.model.UserTrackEnrollment;
import com.necklogic.api.model.enums.ModuleStatus;
import com.necklogic.api.repository.SectionRepository;
import com.necklogic.api.repository.TrackRatingRepository;
import com.necklogic.api.repository.TrackRepository;
import com.necklogic.api.repository.UserProgressRepository;
import com.necklogic.api.repository.UserTrackEnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrackService {

    private final TrackRepository trackRepository;
    private final SectionRepository sectionRepository;
    private final UserProgressRepository progressRepository;
    private final UserTrackEnrollmentRepository enrollmentRepository;
    private final TrackRatingRepository ratingRepository;

    public TrackService(TrackRepository trackRepository,
                        SectionRepository sectionRepository,
                        UserProgressRepository progressRepository,
                        UserTrackEnrollmentRepository enrollmentRepository,
                        TrackRatingRepository ratingRepository) {
        this.trackRepository = trackRepository;
        this.sectionRepository = sectionRepository;
        this.progressRepository = progressRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.ratingRepository = ratingRepository;
    }

    public boolean canEdit(User user, Track track) {
        if (track.isOfficial()) {
            return user.isAdmin();
        }
        return track.getOwner() != null && track.getOwner().getId().equals(user.getId());
    }

    public void requireEditAccess(User user, Track track) {
        if (!canEdit(user, track)) {
            throw new ForbiddenActionException("Você não tem permissão para editar esta trilha.");
        }
    }

    public Track getTrackOrThrow(Long trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new ResourceNotFoundException("Trilha não encontrada com ID: " + trackId));
    }

    public List<TrackResponseDTO> listPublished(User user) {
        Set<Long> enrolledTrackIds = enrolledTrackIds(user);
        return trackRepository.findByPublishedTrue().stream()
                .map(track -> toDTO(track, enrolledTrackIds.contains(track.getId())))
                .toList();
    }

    public List<TrackTeacherSummaryDTO> listOwnedBy(User user) {
        List<Track> owned = new ArrayList<>(trackRepository.findByOwner(user));

        if (user.isAdmin()) {
            trackRepository.findByOfficialTrue().ifPresent(official -> {
                if (owned.stream().noneMatch(t -> t.getId().equals(official.getId()))) {
                    owned.add(official);
                }
            });
        }

        return owned.stream().map(this::toTeacherSummaryDTO).toList();
    }

    private Set<Long> enrolledTrackIds(User user) {
        return enrollmentRepository.findByUser(user).stream()
                .map(enrollment -> enrollment.getTrack().getId())
                .collect(Collectors.toSet());
    }

    List<Long> moduleIdsForTrack(Track track) {
        return sectionRepository.findByTrack(track).stream()
                .flatMap(section -> section.getModules().stream())
                .map(Module::getId)
                .toList();
    }

    public double completionPercentageFor(User user, Track track) {
        List<Long> moduleIds = moduleIdsForTrack(track);
        if (moduleIds.isEmpty()) return 0.0;

        long completedModules = progressRepository.countByUserAndModule_IdInAndStatus(user, moduleIds, ModuleStatus.COMPLETED);
        return completedModules * 100.0 / moduleIds.size();
    }

    @Transactional
    public TrackTeacherSummaryDTO create(User owner, CreateTrackRequestDTO data) {
        if (!owner.isTeacher() && !owner.isAdmin()) {
            throw new ForbiddenActionException("Apenas professores podem criar trilhas.");
        }

        Track track = new Track(data.title(), data.description(), owner, false, Boolean.TRUE.equals(data.published()));
        track.setPaid(Boolean.TRUE.equals(data.paid()));
        track.setPriceCents(data.priceCents());

        return toTeacherSummaryDTO(trackRepository.save(track));
    }

    @Transactional
    public TrackTeacherSummaryDTO update(Long trackId, User user, UpdateTrackRequestDTO data) {
        Track track = getTrackOrThrow(trackId);
        requireEditAccess(user, track);

        if (data.title() != null) track.setTitle(data.title());
        if (data.description() != null) track.setDescription(data.description());
        if (data.published() != null) track.setPublished(data.published());
        if (data.paid() != null) track.setPaid(data.paid());
        if (data.priceCents() != null) track.setPriceCents(data.priceCents());

        return toTeacherSummaryDTO(trackRepository.save(track));
    }

    @Transactional
    public void delete(Long trackId, User user) {
        Track track = getTrackOrThrow(trackId);
        requireEditAccess(user, track);

        if (track.isOfficial()) {
            throw new ForbiddenActionException("A trilha oficial não pode ser excluída.");
        }

        List<Section> sections = sectionRepository.findByTrack(track);
        List<Module> modules = sections.stream().flatMap(section -> section.getModules().stream()).toList();

        ratingRepository.deleteByTrack(track);
        progressRepository.deleteByModuleIn(modules);
        enrollmentRepository.deleteByTrack(track);
        sectionRepository.deleteAll(sections);
        trackRepository.delete(track);
    }

    @Transactional
    public UserTrackEnrollment enroll(Long trackId, User user) {
        Track track = getTrackOrThrow(trackId);

        if (!track.isPublished()) {
            throw new ForbiddenActionException("Esta trilha ainda não foi publicada.");
        }

        return enrollmentRepository.findByUserAndTrack(user, track)
                .orElseGet(() -> enrollmentRepository.save(new UserTrackEnrollment(user, track)));
    }

    public TrackStatsDTO getStats(Long trackId, User owner) {
        Track track = getTrackOrThrow(trackId);
        requireEditAccess(owner, track);

        List<UserTrackEnrollment> enrollments = enrollmentRepository.findByTrack(track);
        List<Long> moduleIds = moduleIdsForTrack(track);

        int enrolledCount = enrollments.size();
        int completedCount = 0;
        if (!moduleIds.isEmpty()) {
            for (UserTrackEnrollment enrollment : enrollments) {
                long completedModules = progressRepository
                        .countByUserAndModule_IdInAndStatus(enrollment.getUser(), moduleIds, ModuleStatus.COMPLETED);
                if (completedModules == moduleIds.size()) {
                    completedCount++;
                }
            }
        }

        double completionRate = enrolledCount == 0 ? 0.0 : (completedCount * 100.0 / enrolledCount);

        List<TrackRating> ratings = ratingRepository.findByTrackOrderByCreatedAtDesc(track);
        Double averageRating = ratings.isEmpty()
                ? null
                : ratings.stream().mapToInt(TrackRating::getStars).average().orElse(0.0);

        long estimatedRevenueCents = estimateRevenueCents(track, enrolledCount);

        List<RatingResponseDTO> ratingDTOs = ratings.stream()
                .map(r -> new RatingResponseDTO(
                        r.getId(),
                        r.getStars(),
                        r.getComment(),
                        r.getUser() != null ? r.getUser().getName() : "—",
                        r.getCreatedAt()))
                .toList();

        return new TrackStatsDTO(
                track.getId(),
                track.getTitle(),
                track.isPaid(),
                track.getPriceCents(),
                enrolledCount,
                completedCount,
                completionRate,
                averageRating,
                ratings.size(),
                estimatedRevenueCents,
                ratingDTOs
        );
    }

    private long estimateRevenueCents(Track track, int enrolledCount) {
        if (!track.isPaid() || track.getPriceCents() == null) return 0;
        return (long) track.getPriceCents() * enrolledCount;
    }

    private TrackTeacherSummaryDTO toTeacherSummaryDTO(Track track) {
        List<UserTrackEnrollment> enrollments = enrollmentRepository.findByTrack(track);
        List<Long> moduleIds = moduleIdsForTrack(track);

        int enrolledCount = enrollments.size();
        int completedCount = 0;
        if (!moduleIds.isEmpty()) {
            for (UserTrackEnrollment enrollment : enrollments) {
                long completedModules = progressRepository
                        .countByUserAndModule_IdInAndStatus(enrollment.getUser(), moduleIds, ModuleStatus.COMPLETED);
                if (completedModules == moduleIds.size()) {
                    completedCount++;
                }
            }
        }

        double completionRate = enrolledCount == 0 ? 0.0 : (completedCount * 100.0 / enrolledCount);

        long ratingCount = ratingRepository.countByTrack(track);
        Double averageRating = null;
        if (ratingCount > 0) {
            averageRating = ratingRepository.findByTrackOrderByCreatedAtDesc(track).stream()
                    .mapToInt(TrackRating::getStars)
                    .average()
                    .orElse(0.0);
        }

        return new TrackTeacherSummaryDTO(
                track.getId(),
                track.getTitle(),
                track.getDescription(),
                track.getOwner() != null ? track.getOwner().getName() : "NeckLogic",
                track.isOfficial(),
                track.isPublished(),
                track.isPaid(),
                track.getPriceCents(),
                enrolledCount,
                completedCount,
                completionRate,
                averageRating,
                (int) ratingCount,
                estimateRevenueCents(track, enrolledCount)
        );
    }

    private TrackResponseDTO toDTO(Track track, boolean enrolled) {
        return new TrackResponseDTO(
                track.getId(),
                track.getTitle(),
                track.getDescription(),
                track.getOwner() != null ? track.getOwner().getName() : "NeckLogic",
                track.isOfficial(),
                track.isPublished(),
                track.isPaid(),
                track.getPriceCents(),
                enrolled
        );
    }
}