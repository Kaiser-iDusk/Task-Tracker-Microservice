package com.taskmanager.tasktracker.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
