package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.shared.enums.TaskPriority;
import com.playko.projectManagement.shared.enums.TaskState;

import java.time.LocalDate;
import java.util.List;

public interface ITaskServicePort {
    List<TaskModel> getTasksByUserEmail();
    void saveTask(TaskModel taskModel);
    void assignTaskToUser(Long taskId, Long userId);
    void reassignTask(Long taskId, Long newUserId);
    void updateTaskDeadline(Long taskId, LocalDate deadline);
    void updateTaskState(Long taskId, TaskState newState);
    List<TaskModel> getTasksByFilters(Long boardId, TaskState state, TaskPriority priority);
    long calculateTaskDuration(Long taskId);
    void deleteTask(Long taskId);
    List<TaskModel> searchTasksByKeyword(String keyword);

}
