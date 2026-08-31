package com.necklogic.api.dto.auth;
public record ResetPasswordDTO(String email, String token, String newPassword) {}