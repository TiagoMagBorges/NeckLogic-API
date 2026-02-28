package com.necklogic.api.controller;

import com.necklogic.api.model.User;
import com.necklogic.api.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PatchMapping("/onboarding")
    public ResponseEntity<Void> completeOnboarding(@AuthenticationPrincipal User user) {
        user.setOnboardingCompleted(true);
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }
}