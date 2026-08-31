package com.necklogic.api.dto.auth;

public record LoginResponseDTO(
    String token,
    boolean onboardingCompleted,
    Integer xp,
    Integer level,
    Integer streak,
    String name,
    String email
) {}