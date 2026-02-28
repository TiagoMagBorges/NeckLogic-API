package com.necklogic.api.controller;

import com.necklogic.api.dto.LessonContentDTO;
import com.necklogic.api.dto.ModuleResponseDTO;
import com.necklogic.api.model.User;
import com.necklogic.api.service.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> complete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        moduleService.completeModule(id, user);
        return ResponseEntity.noContent().build();
    }
}