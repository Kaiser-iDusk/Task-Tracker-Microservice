package com.taskmanager.tasktracker.repository;

import com.taskmanager.tasktracker.dto.TaskDto;
import com.taskmanager.tasktracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {
    List<Task> findByTaskListId(UUID taskListId);
    Optional<Task> findByTaskListIdAndId(UUID taskListID, UUID taskId);
    void deleteByTaskListIdAndId(UUID taskListId, UUID taskId);
}
