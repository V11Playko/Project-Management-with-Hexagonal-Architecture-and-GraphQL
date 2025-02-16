package com.playko.projectManagement.domain.model;

import com.playko.projectManagement.shared.enums.SubTaskState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public class SubTaskModel {
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private SubTaskState state;
    private LocalDate limitDate;
    private TaskModel task;

    public SubTaskModel(Long id, String title, String description, SubTaskState state, LocalDate limitDate, TaskModel task) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.limitDate = limitDate;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public SubTaskState getState() {
        return state;
    }

    public void setState(SubTaskState state) {
        this.state = state;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public TaskModel getTask() {
        return task;
    }

    public void setTask(TaskModel task) {
        this.task = task;
    }
}
