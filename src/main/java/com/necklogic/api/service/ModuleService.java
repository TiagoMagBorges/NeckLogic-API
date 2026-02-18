package com.necklogic.api.service;

import com.necklogic.api.dto.LessonContentDTO;
import com.necklogic.api.dto.ModuleResponseDTO;
import com.necklogic.api.model.Module;
import com.necklogic.api.model.User;
import com.necklogic.api.model.UserProgress;
import com.necklogic.api.model.enums.ModuleStatus;
import com.necklogic.api.repository.ModuleRepository;
import com.necklogic.api.repository.UserProgressRepository;
import com.necklogic.api.repository.UserRepository;
import org.springframework.stereotype.Service;

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
            String secTitle = (module.getSection() != null) ? module.getSection().getTitle() : "General";
            String secDesc = (module.getSection() != null) ? module.getSection().getDescription() : "";

            response.add(new ModuleResponseDTO(
                module.getId(),
                module.getTitle(),
                module.getOrderIndex(),
                status,
                percentage,
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
}