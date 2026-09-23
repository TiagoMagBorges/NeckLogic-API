package com.necklogic.api.controller;

import com.necklogic.api.dto.module.ModuleResponseDTO;
import com.necklogic.api.dto.payment.CheckoutResponseDTO;
import com.necklogic.api.dto.section.CreateSectionRequestDTO;
import com.necklogic.api.dto.section.SectionResponseDTO;
import com.necklogic.api.dto.track.CreateTrackRequestDTO;
import com.necklogic.api.dto.track.TrackResponseDTO;
import com.necklogic.api.dto.track.UpdateTrackRequestDTO;
import com.necklogic.api.model.Section;
import com.necklogic.api.model.User;
import com.necklogic.api.service.ModuleService;
import com.necklogic.api.service.PaymentService;
import com.necklogic.api.service.SectionService;
import com.necklogic.api.service.TrackService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService trackService;
    private final ModuleService moduleService;
    private final SectionService sectionService;
    private final PaymentService paymentService;

    public TrackController(TrackService trackService, ModuleService moduleService, SectionService sectionService, PaymentService paymentService) {
        this.trackService = trackService;
        this.moduleService = moduleService;
        this.sectionService = sectionService;
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<TrackResponseDTO>> listPublished(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(trackService.listPublished(user));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<TrackResponseDTO>> listMine(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(trackService.listOwnedBy(user));
    }

    @PostMapping
    public ResponseEntity<TrackResponseDTO> create(@AuthenticationPrincipal User user, @RequestBody @Valid CreateTrackRequestDTO data) {
        return ResponseEntity.ok(trackService.create(user, data));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrackResponseDTO> update(@PathVariable Long id, @AuthenticationPrincipal User user, @RequestBody UpdateTrackRequestDTO data) {
        return ResponseEntity.ok(trackService.update(id, user, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        trackService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/enroll")
    public ResponseEntity<Void> enroll(@PathVariable Long id, @AuthenticationPrincipal User user) {
        trackService.enroll(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/checkout")
    public ResponseEntity<CheckoutResponseDTO> checkout(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(paymentService.createCheckout(id, user));
    }

    @GetMapping("/{id}/path")
    public ResponseEntity<List<ModuleResponseDTO>> getPath(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(moduleService.getPath(id, user.getEmail()));
    }

    @PostMapping("/{id}/sections")
    public ResponseEntity<Section> createSection(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CreateSectionRequestDTO data) {
        Section section = sectionService.create(id, user, data);
        return ResponseEntity.ok(section);
    }

    @GetMapping("/{id}/sections")
    public ResponseEntity<List<SectionResponseDTO>> listSections(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(sectionService.listByTrack(id, user));
    }
}