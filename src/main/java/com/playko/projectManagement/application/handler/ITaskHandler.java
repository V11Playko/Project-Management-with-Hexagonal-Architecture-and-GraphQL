package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.task.TaskAssignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskDeadlineRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskReassignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskRequestDto;
import com.playko.projectManagement.application.dto.response.TaskResponseDto;
import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.shared.enums.TaskPriority;
import com.playko.projectManagement.shared.enums.TaskState;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ITaskHandler {
    List<TaskResponseDto> getTasksByUserEmail();
    void saveTask(TaskRequestDto taskRequestDto);
    void assignTaskToUser(TaskAssignmentRequestDto taskAssignmentRequestDto);
    void reassignTask(TaskReassignmentRequestDto taskReassignmentRequestDto);
    void updateTaskDeadline(TaskDeadlineRequestDto taskDeadlineRequestDto);
    void updateTaskState(Long taskId, TaskState newState);
    List<TaskModel> getTasksByFilters(Long boardId, TaskState state, TaskPriority priority);
    long calculateTaskDuration(Long taskId);
    void deleteTask(Long taskId);
    List<TaskResponseDto> searchTasksByKeyword(String keyword);

    String storeFile(MultipartFile file) throws Exception;
}
