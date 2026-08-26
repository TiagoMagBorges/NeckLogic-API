package com.necklogic.api.controller;

import com.necklogic.api.dto.CreateModuleRequestDTO;
import com.necklogic.api.dto.UpdateSectionRequestDTO;
import com.necklogic.api.model.Module;
import com.necklogic.api.model.Section;
import com.necklogic.api.model.User;
import com.necklogic.api.service.ModuleService;
import com.necklogic.api.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;
    private final ModuleService moduleService;

    public SectionController(SectionService sectionService, ModuleService moduleService) {
        this.sectionService = sectionService;
        this.moduleService = moduleService;
    }

    @PostMapping("/{id}/skip")
    public ResponseEntity<Void> skipSection(@PathVariable Long id, @AuthenticationPrincipal User user) {
        sectionService.skipSection(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Section> update(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestBody UpdateSectionRequestDTO data) {
        Section section = sectionService.update(id, user, data);
        return ResponseEntity.ok(section);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        sectionService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/modules")
    public ResponseEntity<Module> createModule(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateModuleRequestDTO data) {
        Module module = moduleService.create(id, user, data);
        return ResponseEntity.ok(module);
    }
}
