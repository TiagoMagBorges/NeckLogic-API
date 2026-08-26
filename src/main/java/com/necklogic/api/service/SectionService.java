package com.necklogic.api.service;

import com.necklogic.api.dto.CreateSectionRequestDTO;
import com.necklogic.api.dto.UpdateSectionRequestDTO;
import com.necklogic.api.exception.ResourceNotFoundException;
import com.necklogic.api.model.Module;
import com.necklogic.api.model.Section;
import com.necklogic.api.model.Track;
import com.necklogic.api.model.User;
import com.necklogic.api.model.UserProgress;
import com.necklogic.api.model.enums.ModuleStatus;
import com.necklogic.api.repository.ModuleRepository;
import com.necklogic.api.repository.SectionRepository;
import com.necklogic.api.repository.UserProgressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final ModuleRepository moduleRepository;
    private final UserProgressRepository progressRepository;
    private final TrackService trackService;

    public SectionService(SectionRepository sectionRepository,
                          ModuleRepository moduleRepository,
                          UserProgressRepository progressRepository,
                          TrackService trackService) {
        this.sectionRepository = sectionRepository;
        this.moduleRepository = moduleRepository;
        this.progressRepository = progressRepository;
        this.trackService = trackService;
    }

    @Transactional
    public void skipSection(Long sectionId, User user) {
        Section currentSection = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new RuntimeException("Seção não encontrada"));

        List<Module> modules = currentSection.getModules();
        for (Module module : modules) {
            UserProgress progress = progressRepository.findByUserAndModuleId(user, module.getId())
                    .orElseGet(() -> {
                        UserProgress newProgress = new UserProgress();
                        newProgress.setUser(user);
                        newProgress.setModule(module);
                        return newProgress;
                    });

            progress.setStatus(ModuleStatus.COMPLETED);
            progress.setPercentage(100);
            progressRepository.save(progress);
        }

        sectionRepository.findByTrackAndOrderIndex(currentSection.getTrack(), currentSection.getOrderIndex() + 1)
                .flatMap(moduleRepository::findFirstBySectionOrderByOrderIndexAsc)
                .ifPresent(firstModuleOfNextSection -> {
                    UserProgress nextProgress = progressRepository.findByUserAndModuleId(user, firstModuleOfNextSection.getId())
                            .orElseGet(() -> {
                                UserProgress newProgress = new UserProgress();
                                newProgress.setUser(user);
                                newProgress.setModule(firstModuleOfNextSection);
                                return newProgress;
                            });

                    if (nextProgress.getStatus() == ModuleStatus.LOCKED || nextProgress.getStatus() == null) {
                        nextProgress.setStatus(ModuleStatus.CURRENT);
                        nextProgress.setPercentage(0);
                        progressRepository.save(nextProgress);
                    }
                });
    }

    @Transactional
    public Section create(Long trackId, User user, CreateSectionRequestDTO data) {
        Track track = trackService.getTrackOrThrow(trackId);
        trackService.requireEditAccess(user, track);

        Section section = new Section(data.title(), data.description(), data.orderIndex());
        section.setTrack(track);

        return sectionRepository.save(section);
    }

    @Transactional
    public Section update(Long sectionId, User user, UpdateSectionRequestDTO data) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Seção não encontrada com ID: " + sectionId));
        trackService.requireEditAccess(user, section.getTrack());

        if (data.title() != null) section.setTitle(data.title());
        if (data.description() != null) section.setDescription(data.description());
        if (data.orderIndex() != null) section.setOrderIndex(data.orderIndex());

        return sectionRepository.save(section);
    }

    @Transactional
    public void delete(Long sectionId, User user) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Seção não encontrada com ID: " + sectionId));
        trackService.requireEditAccess(user, section.getTrack());

        progressRepository.deleteByModuleIn(section.getModules());
        sectionRepository.delete(section);
    }
}
