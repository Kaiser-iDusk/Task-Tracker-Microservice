package com.taskmanager.tasktracker.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "task_lists")
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 60)
    private String title;

    private String description;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updatedDate;

    @OneToMany(mappedBy = "taskList", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Task> tasks;

    public TaskList() {
    }

    public TaskList(UUID id, String title, String description, LocalDateTime createdDate, LocalDateTime updatedDate, List<Task> tasks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.tasks = tasks;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TaskList taskList = (TaskList) o;
        return Objects.equals(getId(), taskList.getId()) && Objects.equals(getTitle(), taskList.getTitle()) && Objects.equals(getDescription(), taskList.getDescription()) && Objects.equals(getCreatedDate(), taskList.getCreatedDate()) && Objects.equals(getUpdatedDate(), taskList.getUpdatedDate()) && Objects.equals(getTasks(), taskList.getTasks());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getCreatedDate(), getUpdatedDate(), getTasks());
    }

    @Override
    public String toString() {
        return "TaskList{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", tasks=" + tasks +
                '}';
    }
}
