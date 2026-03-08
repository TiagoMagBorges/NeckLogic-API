package com.necklogic.api.service;

import com.necklogic.api.dto.LessonContentDTO;
import com.necklogic.api.dto.ModuleCompletionResponseDTO;
import com.necklogic.api.dto.ModuleResponseDTO;
import com.necklogic.api.model.Module;
import com.necklogic.api.model.User;
import com.necklogic.api.model.UserProgress;
import com.necklogic.api.model.enums.ModuleStatus;
import com.necklogic.api.repository.ModuleRepository;
import com.necklogic.api.repository.UserProgressRepository;
import com.necklogic.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;
    private final UserProgressRepository progressRepository;
    private final UserRepository userRepository;

    public ModuleService(ModuleRepository moduleRepository, UserProgressRepository progressRepository, UserRepository userRepository) {
        this.moduleRepository = moduleRepository;
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
    }

    public List<ModuleResponseDTO> getUserPath(String userEmail) {
        User user = (User) userRepository.findByEmail(userEmail);
        List<Module> allModules = moduleRepository.findAll();
        List<ModuleResponseDTO> response = new ArrayList<>();

        for (Module module : allModules) {
            Optional<UserProgress> progress = progressRepository.findByUserAndModuleId(user, module.getId());
            ModuleStatus status;
            Integer percentage = 0;

            if (progress.isPresent()) {
                status = progress.get().getStatus();
                percentage = progress.get().getPercentage();
            } else {
                status = (module.getOrderIndex() == 1) ? ModuleStatus.CURRENT : ModuleStatus.LOCKED;
            }

            Long secId = (module.getSection() != null) ? module.getSection().getId() : null;
            String secTitle = (module.getSection() != null) ? module.getSection().getTitle() : "General";
            String secDesc = (module.getSection() != null) ? module.getSection().getDescription() : "";

            response.add(new ModuleResponseDTO(
                    module.getId(),
                    module.getTitle(),
                    module.getOrderIndex(),
                    status,
                    percentage,
                    secId,
                    secTitle,
                    secDesc
            ));
        }
        return response;
    }

    public LessonContentDTO getLessonContent(Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Módulo não encontrado"));

        return new LessonContentDTO(
                module.getId(),
                module.getTitle(),
                module.getContent()
        );
    }

    @Transactional
    public ModuleCompletionResponseDTO completeModule(Long moduleId, User user, Integer mistakesCount) {
        UserProgress currentProgress = progressRepository.findByUserAndModuleId(user, moduleId)
                .orElseGet(() -> {
                    UserProgress newProgress = new UserProgress();
                    newProgress.setUser(user);
                    newProgress.setModule(moduleRepository.findById(moduleId)
                            .orElseThrow(() -> new RuntimeException("Módulo não encontrado")));
                    return newProgress;
                });

        boolean isAlreadyCompleted = currentProgress.getStatus() == ModuleStatus.COMPLETED;
        Module currentModule = currentProgress.getModule();

        int xpGained = 0;
        boolean leveledUp = false;
        int oldLevel = user.getLevel();

        if (!isAlreadyCompleted) {
            currentProgress.setStatus(ModuleStatus.COMPLETED);
            currentProgress.setPercentage(100);
            progressRepository.save(currentProgress);

            int baseReward = currentModule.getXpReward() != null ? currentModule.getXpReward() : 50;
            int penaltyPerMistake = 5;
            int minReward = 10;

            int safeMistakes = mistakesCount != null ? mistakesCount : 0;

            xpGained = Math.max(baseReward - (safeMistakes * penaltyPerMistake), minReward);

            user.addXp(xpGained);
            userRepository.save(user);

            leveledUp = user.getLevel() > oldLevel;
        }

        moduleRepository.findBySectionAndOrderIndex(
                currentModule.getSection(),
                currentModule.getOrderIndex() + 1
        ).ifPresent(nextModule -> {
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
                user.getXp(),
                user.getLevel(),
                leveledUp
        );
    }
}