package com.necklogic.api.dto;

public record LessonContentDTO(
    Long moduleId,
    String title,
    String contentJson
) {}