package com.taskmanager.tasktracker.repository;

import com.taskmanager.tasktracker.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskListRepo extends JpaRepository<TaskList, UUID> {

}
