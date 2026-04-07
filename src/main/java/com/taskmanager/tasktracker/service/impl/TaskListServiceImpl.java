package com.taskmanager.tasktracker.service.impl;

import com.taskmanager.tasktracker.model.TaskList;
import com.taskmanager.tasktracker.repository.TaskListRepo;
import com.taskmanager.tasktracker.service.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepo taskListRepo;

    public TaskListServiceImpl(TaskListRepo taskListRepo) {
        this.taskListRepo = taskListRepo;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepo.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(taskList.getId() != null)
            throw new IllegalArgumentException("Task List already has an Id!");

        if(taskList.getTitle() == null || taskList.getTitle().isBlank())
            throw new IllegalArgumentException("Task List Title cannot be empty!");

        LocalDateTime now = LocalDateTime.now();

        return taskListRepo.save(new TaskList(
                    null,
                    taskList.getTitle(),
                    taskList.getDescription(),
                    now,
                    now,
                    null
                )
        );
    }

    @Override
    public Optional<TaskList> getTaskList(UUID id) {
        return taskListRepo.findById(id);
    }

    @Transactional
    @Override
    public TaskList updateTaskList(UUID id, TaskList taskList) {
        if(taskList.getId() == null)
            throw new IllegalArgumentException("Task List must have an Id!");

        if(!Objects.equals(taskList.getId(), id))
            throw new IllegalArgumentException("Attempting to change task list Id, this action is not permitted!");

        if(taskList.getTitle() == null || taskList.getTitle().isBlank())
            throw new IllegalArgumentException("Task List Title cannot be empty!");

        TaskList existingTaskList = taskListRepo.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Task list not found!")
        );
        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setUpdatedDate(LocalDateTime.now());
        return taskListRepo.save(existingTaskList);
    }

    @Override
    public void deleteTaskList(UUID id) {
        taskListRepo.deleteById(id);
    }
}
