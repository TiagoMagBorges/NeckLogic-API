package com.necklogic.api.controller;

import com.necklogic.api.dto.rating.MyRatingResponseDTO;
import com.necklogic.api.dto.rating.SubmitRatingRequestDTO;
import com.necklogic.api.model.User;
import com.necklogic.api.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tracks/{id}/rating")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/mine")
    public ResponseEntity<MyRatingResponseDTO> getMyRating(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ratingService.getMyRating(id, user));
    }

    @PostMapping
    public ResponseEntity<MyRatingResponseDTO> submitRating(
            @PathVariable Long id,
            @AuthenticationPrincipal User user,
            @RequestBody @Valid SubmitRatingRequestDTO data) {
        return ResponseEntity.ok(ratingService.submitRating(id, user, data));
    }
}