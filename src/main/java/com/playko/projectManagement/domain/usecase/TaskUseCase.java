package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.domain.api.ITaskServicePort;
import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.domain.spi.ITaskPersistencePort;
import com.playko.projectManagement.shared.enums.TaskPriority;
import com.playko.projectManagement.shared.enums.TaskState;

import java.time.LocalDate;
import java.util.List;

public class TaskUseCase implements ITaskServicePort {
    private final ITaskPersistencePort taskPersistencePort;

    public TaskUseCase(ITaskPersistencePort taskPersistencePort) {
        this.taskPersistencePort = taskPersistencePort;
    }

    @Override
    public List<TaskModel> getTasksByUserEmail() {
        return taskPersistencePort.getTasksByUserEmail();
    }

    @Override
    public void saveTask(TaskModel taskModel) {
        taskPersistencePort.saveTask(taskModel);
    }

    @Override
    public void assignTaskToUser(Long taskId, Long userId) {
        taskPersistencePort.assignTaskToUser(taskId, userId);
    }

    @Override
    public void reassignTask(Long taskId, Long newUserId) {
        taskPersistencePort.reassignTask(taskId, newUserId);
    }

    @Override
    public void updateTaskDeadline(Long taskId, LocalDate deadline) {
        taskPersistencePort.updateTaskDeadline(taskId, deadline);
    }

    @Override
    public void updateTaskState(Long taskId, TaskState newState) {
        taskPersistencePort.updateTaskState(taskId, newState);
    }

    @Override
    public List<TaskModel> getTasksByFilters(Long boardId, TaskState state, TaskPriority priority) {
        return taskPersistencePort.getTasksByFilters(boardId, state, priority);
    }

    @Override
    public long calculateTaskDuration(Long taskId) {
        return taskPersistencePort.calculateTaskDuration(taskId);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskPersistencePort.deleteTask(taskId);
    }

    @Override
    public List<TaskModel> searchTasksByKeyword(String keyword) {
        return taskPersistencePort.searchTasksByKeyword(keyword);
    }
}
