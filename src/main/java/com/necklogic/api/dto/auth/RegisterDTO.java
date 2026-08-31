package com.necklogic.api.dto.auth;

public record RegisterDTO(String name, String email, String password, Boolean asTeacher) {
}
