package com.necklogic.api.dto.module;

public record LessonContentDTO(
    Long moduleId,
    String title,
    String contentJson
) {}