package com.taskmanager.tasktracker.service.impl;

import com.taskmanager.tasktracker.model.Task;
import com.taskmanager.tasktracker.model.TaskList;
import com.taskmanager.tasktracker.model.TaskPriority;
import com.taskmanager.tasktracker.model.TaskStatus;
import com.taskmanager.tasktracker.repository.TaskListRepo;
import com.taskmanager.tasktracker.repository.TaskRepo;
import com.taskmanager.tasktracker.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final TaskListRepo taskListRepo;

    public TaskServiceImpl(TaskRepo taskRepo, TaskListRepo taskListRepo) {
        this.taskRepo = taskRepo;
        this.taskListRepo = taskListRepo;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepo.findByTaskListId(taskListId);
    }

    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if(task.getId() != null)
            throw new IllegalArgumentException("Task already has an Id!");

        if(task.getTitle() == null || task.getTitle().isBlank())
            throw new IllegalArgumentException("Task title is required!");

        TaskPriority priority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.LOW);
        TaskStatus status = TaskStatus.OPEN;

        TaskList existingTaskList = taskListRepo.findById(taskListId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Task list with id " + taskListId + " does not exist!")
                );

        LocalDateTime now = LocalDateTime.now();

        Task newTask = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                status,
                priority,
                now,
                now,
                existingTaskList
        );

        return taskRepo.save(newTask);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepo.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(task.getId() == null){
            throw new IllegalArgumentException("Task must have an Id!");
        }

        if(task.getTitle() == null || task.getTitle().isBlank())
            throw new IllegalArgumentException("Task title is required!");

        if(Objects.equals(taskId, task.getId())){
            throw new IllegalArgumentException("Task Ids should match!");
        }

        if(task.getPriority() == null)
            throw new IllegalArgumentException("Task priority is required!");

        if(task.getStatus() == null)
            throw new IllegalArgumentException("Task status is required!");

        Task existingTask = taskRepo.findByTaskListIdAndId(taskListId, taskId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Task does not exist!")
                );
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdatedDate(LocalDateTime.now());

        return taskRepo.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepo.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
