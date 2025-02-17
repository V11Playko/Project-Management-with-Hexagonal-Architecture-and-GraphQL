package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.domain.model.TaskModel;

import java.time.LocalDate;

public interface ITaskServicePort {
    void saveTask(TaskModel taskModel);
    void assignTaskToUser(Long taskId, Long userId);
    void reassignTask(Long taskId, Long newUserId);
    void updateTaskDeadline(Long taskId, LocalDate deadline);
}
