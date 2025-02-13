package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.domain.model.TaskModel;

public interface ITaskServicePort {
    void saveTask(TaskModel taskModel);
    void assignTaskToUser(Long taskId, Long userId);
}
