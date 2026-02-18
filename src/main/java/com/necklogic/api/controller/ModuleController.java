package com.necklogic.api.controller;

import com.necklogic.api.dto.ModuleResponseDTO;
import com.necklogic.api.service.ModuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
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
}