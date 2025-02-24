package com.playko.projectManagement.domain.model;


import java.time.LocalDateTime;

public class CommentModel {
    private Long id;
    private String content;
    private LocalDateTime createdAt;

    private TaskModel task;
    private UserModel user;

    public CommentModel(Long id, String content, LocalDateTime createdAt, TaskModel task, UserModel user) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.task = task;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TaskModel getTask() {
        return task;
    }

    public void setTask(TaskModel task) {
        this.task = task;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
