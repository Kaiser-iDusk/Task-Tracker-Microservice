package com.taskmanager.tasktracker.mapper;

import com.taskmanager.tasktracker.dto.TaskDto;
import com.taskmanager.tasktracker.model.Task;

public interface TaskMapper {
    Task fromDto(TaskDto taskDto);

    TaskDto toDto(Task task);
}
