package com.necklogic.api.controller;

import com.necklogic.api.dto.module.*;
import com.necklogic.api.model.Module;
import com.necklogic.api.model.User;
import com.necklogic.api.service.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public ResponseEntity<List<ModuleResponseDTO>> getPath(@AuthenticationPrincipal UserDetails userDetails) {
        List<ModuleResponseDTO> path = moduleService.getUserPath(userDetails.getUsername());
        return ResponseEntity.ok(path);
    }

    @GetMapping("/{id}/content")
    public ResponseEntity<LessonContentDTO> getContent(@PathVariable Long id) {
        LessonContentDTO content = moduleService.getLessonContent(id);
        return ResponseEntity.ok(content);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<ModuleCompletionResponseDTO> complete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestBody ModuleCompletionRequestDTO request) {
        ModuleCompletionResponseDTO response = moduleService.completeModule(id, user, request.mistakesCount());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> update(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestBody UpdateModuleRequestDTO data) {
        Module module = moduleService.update(id, user, data);
        return ResponseEntity.ok(module);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        moduleService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleDetailDTO> getForEdit(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(moduleService.getForEdit(id, user));
    }
}
