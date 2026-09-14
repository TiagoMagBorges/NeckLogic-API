package com.necklogic.api.service;

import com.necklogic.api.dto.module.*;
import com.necklogic.api.dto.section.SkipTestCompletionResponseDTO;
import com.necklogic.api.exception.ResourceNotFoundException;
import com.necklogic.api.model.Module;
import com.necklogic.api.model.Section;
import com.necklogic.api.model.Track;
import com.necklogic.api.model.User;
import com.necklogic.api.model.UserProgress;
import com.necklogic.api.model.UserTrackEnrollment;
import com.necklogic.api.model.enums.ModuleStatus;
import com.necklogic.api.repository.ModuleRepository;
import com.necklogic.api.repository.SectionRepository;
import com.necklogic.api.repository.TrackRepository;
import com.necklogic.api.repository.UserProgressRepository;
import com.necklogic.api.repository.UserRepository;
import com.necklogic.api.repository.UserTrackEnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final SectionRepository sectionRepository;
    private final UserProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final UserTrackEnrollmentRepository enrollmentRepository;
    private final TrackRepository trackRepository;
    private final TrackService trackService;

    public ModuleService(ModuleRepository moduleRepository,
                         SectionRepository sectionRepository,
                         UserProgressRepository progressRepository,
                         UserRepository userRepository,
                         UserTrackEnrollmentRepository enrollmentRepository,
                         TrackRepository trackRepository,
                         TrackService trackService) {
        this.moduleRepository = moduleRepository;
        this.sectionRepository = sectionRepository;
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.trackRepository = trackRepository;
        this.trackService = trackService;
    }

    public List<ModuleResponseDTO> getUserPath(String userEmail) {
        Track officialTrack = trackRepository.findByOfficialTrue()
                .orElseThrow(() -> new IllegalStateException("No official track configured"));

        return getPath(officialTrack.getId(), userEmail);
    }

    public List<ModuleResponseDTO> getPath(Long trackId, String userEmail) {
        User user = (User) userRepository.findByEmail(userEmail);
        Track track = trackService.getTrackOrThrow(trackId);
        List<Module> trackModules = moduleRepository.findBySectionTrack(track);
        List<ModuleResponseDTO> response = new ArrayList<>();

        for (Module module : trackModules) {
            if (module.isSkipTest()) continue;

            Optional<UserProgress> progress = progressRepository.findByUserAndModuleId(user, module.getId());
            ModuleStatus status;
            Integer percentage = 0;

            if (progress.isPresent()) {
                status = progress.get().getStatus();
                percentage = progress.get().getPercentage();
            } else {
                status = (module.getOrderIndex() == 1) ? ModuleStatus.CURRENT : ModuleStatus.LOCKED;
            }

            Section section = module.getSection();
            Long secId = (section != null) ? section.getId() : null;
            String secTitle = (section != null) ? section.getTitle() : "General";
            String secDesc = (section != null) ? section.getDescription() : "";
            Integer secOrderIndex = (section != null) ? section.getOrderIndex() : null;
            Boolean secSkipRequiresTest = (section != null) && section.isSkipRequiresTest();
            Long secSkipTestModuleId = (section != null && section.getSkipTestModule() != null) ? section.getSkipTestModule().getId() : null;
            Double secSkipPassThreshold = (section != null) ? section.getSkipPassThreshold() : null;

            response.add(new ModuleResponseDTO(
                    module.getId(),
                    module.getTitle(),
                    module.getOrderIndex(),
                    status,
                    percentage,
                    secId,
                    secTitle,
                    secDesc,
                    secOrderIndex,
                    secSkipRequiresTest,
                    secSkipTestModuleId,
                    secSkipPassThreshold
            ));
        }
        return response;
    }

    public LessonContentDTO getLessonContent(Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Módulo não encontrado com ID: " + moduleId));

        return new LessonContentDTO(
                module.getId(),
                module.getTitle(),
                module.getContent()
        );
    }

    @Transactional
    public ModuleCompletionResponseDTO completeModule(Long moduleId, User user, Double mistakesCount) {
        UserProgress currentProgress = progressRepository.findByUserAndModuleId(user, moduleId)
                .orElseGet(() -> {
                    UserProgress newProgress = new UserProgress();
                    newProgress.setUser(user);
                    newProgress.setModule(moduleRepository.findById(moduleId)
                            .orElseThrow(() -> new ResourceNotFoundException("Módulo não encontrado com ID: " + moduleId)));
                    return newProgress;
                });

        boolean isAlreadyCompleted = currentProgress.getStatus() == ModuleStatus.COMPLETED;
        Module currentModule = currentProgress.getModule();
        Track track = currentModule.getSection().getTrack();
        UserTrackEnrollment enrollment = getOrCreateEnrollment(user, track);

        int xpGained = 0;
        boolean leveledUp = false;
        int oldLevel = enrollment.getLevel();

        if (!isAlreadyCompleted) {
            currentProgress.setStatus(ModuleStatus.COMPLETED);
            currentProgress.setPercentage(100);
            progressRepository.save(currentProgress);

            int baseReward = currentModule.getXpReward() != null ? currentModule.getXpReward() : 50;
            int penaltyPerMistake = 5;
            int minReward = 10;

            double safeMistakes = mistakesCount != null ? mistakesCount : 0;

            xpGained = Math.max((int) Math.round(baseReward - (safeMistakes * penaltyPerMistake)), minReward);

            LocalDate today = LocalDate.now();
            if (enrollment.getLastActivityDate() == null || enrollment.getLastActivityDate().isBefore(today.minusDays(1))) {
                enrollment.setCurrentStreak(1);
                enrollment.setLastActivityDate(today);
            } else if (enrollment.getLastActivityDate().isEqual(today.minusDays(1))) {
                enrollment.setCurrentStreak(enrollment.getCurrentStreak() + 1);
                enrollment.setLastActivityDate(today);
            }

            enrollment.addXp(xpGained);
            enrollmentRepository.save(enrollment);

            leveledUp = enrollment.getLevel() > oldLevel;
        }

        Optional<Module> nextModuleInTrack = moduleRepository
                .findFirstBySectionAndOrderIndexOrderByIdAsc(currentModule.getSection(), currentModule.getOrderIndex() + 1)
                .or(() -> sectionRepository
                        .findByTrackAndOrderIndex(track, currentModule.getSection().getOrderIndex() + 1)
                        .flatMap(moduleRepository::findFirstBySectionOrderByOrderIndexAsc));

        nextModuleInTrack.ifPresent(nextModule -> {
            UserProgress nextProgress = progressRepository.findByUserAndModuleId(user, nextModule.getId())
                    .orElseGet(() -> {
                        UserProgress newProgress = new UserProgress();
                        newProgress.setUser(user);
                        newProgress.setModule(nextModule);
                        return newProgress;
                    });

            if (nextProgress.getStatus() == ModuleStatus.LOCKED || nextProgress.getStatus() == null) {
                nextProgress.setStatus(ModuleStatus.CURRENT);
                progressRepository.save(nextProgress);
            }
        });

        return new ModuleCompletionResponseDTO(
                moduleId,
                xpGained,
                enrollment.getXp(),
                enrollment.getLevel(),
                leveledUp,
                enrollment.getCurrentStreak()
        );
    }

    @Transactional
    public SkipTestCompletionResponseDTO completeSkipTest(Long sectionId, User user, Double mistakesCount, Integer drillCount) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Seção não encontrada com ID: " + sectionId));

        if (!section.isSkipRequiresTest() || section.getSkipTestModule() == null) {
            throw new IllegalStateException("Esta seção não tem um teste de pular configurado");
        }

        double safeMistakes = mistakesCount != null ? mistakesCount : 0;
        int safeDrillCount = (drillCount != null && drillCount > 0) ? drillCount : 1;
        double scorePercentage = Math.max(0, ((safeDrillCount - safeMistakes) / safeDrillCount) * 100);

        double threshold = section.getSkipPassThreshold() != null ? section.getSkipPassThreshold() : 100;
        boolean passed = scorePercentage >= threshold;

        if (!passed) {
            return new SkipTestCompletionResponseDTO(false, scorePercentage, 0, null, null, false, null);
        }

        ModuleCompletionResponseDTO completion = completeModule(section.getSkipTestModule().getId(), user, mistakesCount);

        return new SkipTestCompletionResponseDTO(
                true,
                scorePercentage,
                completion.xpGained(),
                completion.totalXp(),
                completion.level(),
                completion.leveledUp(),
                completion.streak()
        );
    }

    @Transactional
    public Module create(Long sectionId, User user, CreateModuleRequestDTO data) {
        Section section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new ResourceNotFoundException("Seção não encontrada com ID: " + sectionId));
        trackService.requireEditAccess(user, section.getTrack());

        Module module = new Module(data.title(), data.orderIndex(), section, data.content());
        if (data.xpReward() != null) module.setXpReward(data.xpReward());
        if (data.isSkipTest() != null) module.setSkipTest(data.isSkipTest());

        return moduleRepository.save(module);
    }

    @Transactional
    public Module update(Long moduleId, User user, UpdateModuleRequestDTO data) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Módulo não encontrado com ID: " + moduleId));
        trackService.requireEditAccess(user, module.getSection().getTrack());

        if (data.title() != null) module.setTitle(data.title());
        if (data.orderIndex() != null) module.setOrderIndex(data.orderIndex());
        if (data.xpReward() != null) module.setXpReward(data.xpReward());
        if (data.content() != null) module.setContent(data.content());
        if (data.isSkipTest() != null) module.setSkipTest(data.isSkipTest());

        return moduleRepository.save(module);
    }

    @Transactional
    public void delete(Long moduleId, User user) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Módulo não encontrado com ID: " + moduleId));
        trackService.requireEditAccess(user, module.getSection().getTrack());

        progressRepository.deleteByModuleIn(List.of(module));
        moduleRepository.delete(module);
    }

    public ModuleDetailDTO getForEdit(Long moduleId, User user) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Módulo não encontrado com ID: " + moduleId));
        trackService.requireEditAccess(user, module.getSection().getTrack());

        return new ModuleDetailDTO(
                module.getId(),
                module.getTitle(),
                module.getOrderIndex(),
                module.getXpReward(),
                module.getContent(),
                module.getSection().getId(),
                module.isSkipTest()
        );
    }

    private UserTrackEnrollment getOrCreateEnrollment(User user, Track track) {
        return enrollmentRepository.findByUserAndTrack(user, track)
                .orElseGet(() -> enrollmentRepository.save(new UserTrackEnrollment(user, track)));
    }
}