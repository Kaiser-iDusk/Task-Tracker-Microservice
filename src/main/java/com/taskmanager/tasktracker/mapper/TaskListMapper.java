package com.taskmanager.tasktracker.mapper;

import com.taskmanager.tasktracker.dto.TaskListDto;
import com.taskmanager.tasktracker.model.TaskList;

public interface TaskListMapper {
    TaskList fromDto(TaskListDto taskListDto);

    TaskListDto toDto(TaskList taskList);
}
