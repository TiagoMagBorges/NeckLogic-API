package com.necklogic.api.dto;
public record ResetPasswordDTO(String email, String token, String newPassword) {}