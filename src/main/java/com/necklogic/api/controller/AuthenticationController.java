package com.necklogic.api.controller;

import com.necklogic.api.dto.*;
import com.necklogic.api.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            return ResponseEntity.ok(authenticationService.login(data));
        } catch (IllegalStateException e) {
            if ("ACCOUNT_DISABLED".equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "ACCOUNT_DISABLED"));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Credenciais inválidas"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        try {
            authenticationService.registerUser(data);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message", "Email já registrado e ativado."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Dados inválidos."));
        }
    }

    @PostMapping("/verify-account")
    public ResponseEntity<?> verifyAccount(@RequestBody @Valid OtpVerificationDTO data) {
        try {
            return ResponseEntity.ok(authenticationService.verifyAccount(data.email(), data.token()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Código inválido ou expirado."));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody @Valid ForgotPasswordDTO data) {
        authenticationService.requestPasswordReset(data.email());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordDTO data) {
        try {
            authenticationService.resetPassword(data.email(), data.token(), data.newPassword());
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Código inválido ou expirado."));
        }
    }
}