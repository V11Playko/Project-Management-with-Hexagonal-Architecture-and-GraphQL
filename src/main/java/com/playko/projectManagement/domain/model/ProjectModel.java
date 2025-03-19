package com.playko.projectManagement.domain.model;

import com.playko.projectManagement.shared.enums.ProjectState;
import com.playko.projectManagement.shared.enums.TaskState;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private List<String> restrictedUsers;

    public ProjectModel(Long id, String name, String description, LocalDate creationDate, LocalDate finishedDate, ProjectState state, String owner, List<BoardModel> boards, List<TaskModel> tasks, List<String> restrictedUsers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.finishedDate = finishedDate;
        this.state = state;
        this.owner = owner;
        this.boards = boards;
        this.tasks = tasks;
        this.restrictedUsers = restrictedUsers;
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

    public List<String> getRestrictedUsers() {
        return restrictedUsers;
    }

    public void setRestrictedUsers(List<String> restrictedUsers) {
        this.restrictedUsers = restrictedUsers;
    }

    public double getCompletionPercentage() {
        if (tasks == null || tasks.isEmpty()) {
            return 0.0;
        }

        long completedTasks = tasks.stream()
                .filter(task -> task.getState() == TaskState.DONE)
                .count();

        return (double) completedTasks / tasks.size() * 100;
    }

    public long getDaysRemaining() {
        if (finishedDate == null) {
            return -1; // Si no hay fecha límite definida
        }

        return ChronoUnit.DAYS.between(LocalDate.now(), finishedDate);
    }

    public String getProgressStatus() {
        if (finishedDate == null || tasks.isEmpty()) {
            return "No disponible";
        }

        double daysPassed = ChronoUnit.DAYS.between(creationDate, LocalDate.now());
        double totalDuration = ChronoUnit.DAYS.between(creationDate, finishedDate);
        double expectedCompletion = (daysPassed / totalDuration) * 100;
        double actualCompletion = getCompletionPercentage();

        if (actualCompletion >= expectedCompletion) {
            return "A tiempo";
        } else {
            return "Atrasado";
        }
    }

    public Map<TaskState, Long> getTaskCountsByState() {
        if (tasks == null || tasks.isEmpty()) {
            return Map.of(
                    TaskState.DONE, 0L,
                    TaskState.IN_PROGRESS, 0L,
                    TaskState.IN_PROGRESS, 0L
            );
        }

        return tasks.stream().collect(Collectors.groupingBy(TaskModel::getState, Collectors.counting()));
    }
}
