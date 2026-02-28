package com.necklogic.api.controller;

import com.necklogic.api.model.User;
import com.necklogic.api.service.SectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/{id}/skip")
    public ResponseEntity<Void> skipSection(@PathVariable Long id, @AuthenticationPrincipal User user) {
        sectionService.skipSection(id, user);
        return ResponseEntity.noContent().build();
    }
}