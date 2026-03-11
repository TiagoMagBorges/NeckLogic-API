package com.necklogic.api.controller;

import com.necklogic.api.dto.UpdatePasswordRequestDTO;
import com.necklogic.api.dto.UpdateProfileRequestDTO;
import com.necklogic.api.model.User;
import com.necklogic.api.repository.UserRepository;
import com.necklogic.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PatchMapping("/onboarding")
    public ResponseEntity<Void> completeOnboarding(@AuthenticationPrincipal User user) {
        user.setOnboardingCompleted(true);
        userRepository.save(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<Map<String, String>> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UpdateProfileRequestDTO request) {
        User updatedUser = userService.updateProfile(user, request);
        return ResponseEntity.ok(Map.of(
                "name", updatedUser.getName(),
                "email", updatedUser.getEmail()
        ));
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid UpdatePasswordRequestDTO request) {
        userService.updatePassword(user, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/account")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal User user) {
        userService.deleteAccount(user);
        return ResponseEntity.noContent().build();
    }
}