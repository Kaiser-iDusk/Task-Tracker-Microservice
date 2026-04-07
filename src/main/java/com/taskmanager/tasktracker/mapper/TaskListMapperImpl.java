package com.taskmanager.tasktracker.mapper;

import com.taskmanager.tasktracker.dto.TaskListDto;
import com.taskmanager.tasktracker.model.Task;
import com.taskmanager.tasktracker.model.TaskList;
import com.taskmanager.tasktracker.model.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper{
    private final TaskMapperImpl taskMapper;

    public TaskListMapperImpl(TaskMapperImpl taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                null,
                null,
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList()
                        ).orElse(null)
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        int totalTasks = Optional.ofNullable(taskList.getTasks())
                .map(List::size)
                .orElse(0);

        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                totalTasks,
                getTaskProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::toDto)
                                .toList()
                        ).orElse(null)
        );
    }

    private Double getTaskProgress(List<Task> tasks){
        if(tasks == null) return null;

        long closedTaskCount = tasks.stream()
                .filter(task -> TaskStatus.CLOSED == task.getStatus())
                .count();

        return (double) closedTaskCount / tasks.size();
    }
}
