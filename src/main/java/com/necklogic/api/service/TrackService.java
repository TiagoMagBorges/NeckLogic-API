package com.necklogic.api.service;

import com.necklogic.api.dto.track.CreateTrackRequestDTO;
import com.necklogic.api.dto.track.TrackResponseDTO;
import com.necklogic.api.dto.track.UpdateTrackRequestDTO;
import com.necklogic.api.exception.ForbiddenActionException;
import com.necklogic.api.exception.ResourceNotFoundException;
import com.necklogic.api.model.Module;
import com.necklogic.api.model.Section;
import com.necklogic.api.model.Track;
import com.necklogic.api.model.User;
import com.necklogic.api.model.UserTrackEnrollment;
import com.necklogic.api.repository.SectionRepository;
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

    public TrackService(TrackRepository trackRepository,
                        SectionRepository sectionRepository,
                        UserProgressRepository progressRepository,
                        UserTrackEnrollmentRepository enrollmentRepository) {
        this.trackRepository = trackRepository;
        this.sectionRepository = sectionRepository;
        this.progressRepository = progressRepository;
        this.enrollmentRepository = enrollmentRepository;
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

    public List<TrackResponseDTO> listOwnedBy(User user) {
        List<Track> owned = new ArrayList<>(trackRepository.findByOwner(user));

        if (user.isAdmin()) {
            trackRepository.findByOfficialTrue().ifPresent(official -> {
                if (owned.stream().noneMatch(t -> t.getId().equals(official.getId()))) {
                    owned.add(official);
                }
            });
        }

        Set<Long> enrolledTrackIds = enrolledTrackIds(user);
        return owned.stream().map(track -> toDTO(track, enrolledTrackIds.contains(track.getId()))).toList();
    }

    private Set<Long> enrolledTrackIds(User user) {
        return enrollmentRepository.findByUser(user).stream()
                .map(enrollment -> enrollment.getTrack().getId())
                .collect(Collectors.toSet());
    }

    @Transactional
    public TrackResponseDTO create(User owner, CreateTrackRequestDTO data) {
        if (!owner.isTeacher() && !owner.isAdmin()) {
            throw new ForbiddenActionException("Apenas professores podem criar trilhas.");
        }

        Track track = new Track(data.title(), data.description(), owner, false, false);
        track.setPaid(Boolean.TRUE.equals(data.paid()));
        track.setPriceCents(data.priceCents());

        return toDTO(trackRepository.save(track), false);
    }

    @Transactional
    public TrackResponseDTO update(Long trackId, User user, UpdateTrackRequestDTO data) {
        Track track = getTrackOrThrow(trackId);
        requireEditAccess(user, track);

        if (data.title() != null) track.setTitle(data.title());
        if (data.description() != null) track.setDescription(data.description());
        if (data.published() != null) track.setPublished(data.published());
        if (data.paid() != null) track.setPaid(data.paid());
        if (data.priceCents() != null) track.setPriceCents(data.priceCents());

        boolean enrolled = enrollmentRepository.findByUserAndTrack(user, track).isPresent();
        return toDTO(trackRepository.save(track), enrolled);
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
        if (track.isPaid()) {
            throw new ForbiddenActionException("Esta trilha é paga. Finalize a compra para se matricular.");
        }

        return doEnroll(user, track);
    }

    @Transactional
    public UserTrackEnrollment grantPaidEnrollment(User user, Track track) {
        return doEnroll(user, track);
    }

    private UserTrackEnrollment doEnroll(User user, Track track) {
        return enrollmentRepository.findByUserAndTrack(user, track)
                .orElseGet(() -> enrollmentRepository.save(new UserTrackEnrollment(user, track)));
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