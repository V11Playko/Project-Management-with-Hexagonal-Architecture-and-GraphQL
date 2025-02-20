package com.playko.projectManagement.domain.model;

import com.playko.projectManagement.shared.enums.ProjectState;

import java.time.LocalDate;
import java.util.List;

public class ProjectModel {
    private Long id;
    private String name;
    private String description;
    private LocalDate creationDate;
    private LocalDate finishedDate;
    private ProjectState state;
    private String owner;
    private List<BoardModel> boards;
    private List<TaskModel> tasks;

    public ProjectModel(Long id, String name, String description, LocalDate creationDate, LocalDate finishedDate, ProjectState state, String owner, List<BoardModel> boards, List<TaskModel> tasks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.finishedDate = finishedDate;
        this.state = state;
        this.owner = owner;
        this.boards = boards;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(LocalDate finishedDate) {
        this.finishedDate = finishedDate;
    }

    public ProjectState getState() {
        return state;
    }

    public void setState(ProjectState state) {
        this.state = state;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<BoardModel> getBoards() {
        return boards;
    }

    public void setBoards(List<BoardModel> boards) {
        this.boards = boards;
    }

    public List<TaskModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskModel> tasks) {
        this.tasks = tasks;
    }
}
