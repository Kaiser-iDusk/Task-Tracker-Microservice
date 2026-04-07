package com.taskmanager.tasktracker.dto;

import com.taskmanager.tasktracker.model.TaskPriority;
import com.taskmanager.tasktracker.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) {
}
