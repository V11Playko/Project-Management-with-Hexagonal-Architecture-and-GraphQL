package com.playko.projectManagement.domain.model;

import com.playko.projectManagement.shared.enums.TaskPriority;
import com.playko.projectManagement.shared.enums.TaskState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.List;

public class TaskModel {
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskState state;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    private LocalDate limitDate;
    private ProjectModel project;
    private BoardColumnModel boardColumn;
    private List<SubTaskModel> subtasks;
    private UserModel assignedUserId;

    public TaskModel(Long id, String title, String description, TaskState state, TaskPriority priority, LocalDate limitDate, ProjectModel project, BoardColumnModel boardColumn, List<SubTaskModel> subtasks, UserModel assignedUserId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = state;
        this.priority = priority;
        this.limitDate = limitDate;
        this.project = project;
        this.boardColumn = boardColumn;
        this.subtasks = subtasks;
        this.assignedUserId = assignedUserId;
    }

    public TaskModel() {

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

    public TaskState getState() {
        return state;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public LocalDate getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDate limitDate) {
        this.limitDate = limitDate;
    }

    public ProjectModel getProject() {
        return project;
    }

    public void setProject(ProjectModel project) {
        this.project = project;
    }

    public BoardColumnModel getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(BoardColumnModel boardColumn) {
        this.boardColumn = boardColumn;
    }

    public List<SubTaskModel> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(List<SubTaskModel> subtasks) {
        this.subtasks = subtasks;
    }

    public UserModel getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(UserModel assignedUserId) {
        this.assignedUserId = assignedUserId;
    }
}
