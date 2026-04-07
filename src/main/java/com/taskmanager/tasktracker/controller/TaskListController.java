package com.taskmanager.tasktracker.controller;

import com.taskmanager.tasktracker.dto.TaskListDto;
import com.taskmanager.tasktracker.mapper.TaskListMapper;
import com.taskmanager.tasktracker.model.TaskList;
import com.taskmanager.tasktracker.service.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/task-lists")
public class TaskListController {
    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }

    @GetMapping
    public List<TaskListDto> listTaskLists(){
        return taskListService.listTaskLists()
                .stream()
                .map(taskListMapper::toDto)
                .toList();
    }

    @PostMapping
    public TaskListDto createTaskList(
            @RequestBody TaskListDto taskListDto
    ){
        TaskList createdTaskList = taskListService.createTaskList(
                taskListMapper.fromDto(taskListDto)
        );

        return taskListMapper.toDto(createdTaskList);
    }

    @GetMapping("/{id}")
    public Optional<TaskListDto> getTaskList(
            @PathVariable("id") UUID task_list_id
    ){
        return taskListService.getTaskList(task_list_id).map(taskListMapper::toDto);
    }

    @PutMapping("/{id}")
    public TaskListDto updateTaskList(
            @PathVariable("id") UUID taskListId,
            @RequestBody TaskListDto taskListDto
    ) {
        TaskList updatedTaskList = taskListService.updateTaskList(
                taskListId,
                taskListMapper.fromDto(taskListDto)
        );
        return taskListMapper.toDto(updatedTaskList);
    }

    @DeleteMapping("/{id}")
    public void deleteTaskList(
            @PathVariable("id") UUID id
    ){
        taskListService.deleteTaskList(id);
    }
}
