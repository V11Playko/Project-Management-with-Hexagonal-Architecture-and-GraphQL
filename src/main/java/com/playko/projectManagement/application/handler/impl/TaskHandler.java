package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.task.TaskAssignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskDeadlineRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskReassignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskRequestDto;
import com.playko.projectManagement.application.dto.response.TaskResponseDto;
import com.playko.projectManagement.application.handler.ITaskHandler;
import com.playko.projectManagement.application.mapper.request.ITaskRequestMapper;
import com.playko.projectManagement.domain.api.ITaskServicePort;
import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.shared.enums.TaskPriority;
import com.playko.projectManagement.shared.enums.TaskState;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskHandler implements ITaskHandler {
    private final ITaskServicePort taskServicePort;
    private final ITaskRequestMapper taskRequestMapper;

    @Override
    public List<TaskResponseDto> getTasksByUserEmail() {
        List<TaskModel> taskModels = taskServicePort.getTasksByUserEmail();
        return taskRequestMapper.toDtoList(taskModels);
    }

    @Override
    public void saveTask(TaskRequestDto taskRequestDto) {
        TaskModel taskModel = taskRequestMapper.toModel(taskRequestDto);
        taskServicePort.saveTask(taskModel);
    }

    @Override
    public void assignTaskToUser(TaskAssignmentRequestDto taskAssignmentRequestDto) {
        TaskModel taskModel = taskRequestMapper.taskAssignmentToModel(taskAssignmentRequestDto);
        taskServicePort.assignTaskToUser(taskModel.getId(), taskModel.getAssignedUserId().getId());
    }

    @Override
    public void reassignTask(TaskReassignmentRequestDto taskReassignmentRequestDto) {
        taskServicePort.reassignTask(taskReassignmentRequestDto.getTaskId(), taskReassignmentRequestDto.getNewUserId());
    }

    @Override
    public void updateTaskDeadline(TaskDeadlineRequestDto taskDeadlineRequestDto) {
        taskServicePort.updateTaskDeadline(taskDeadlineRequestDto.getTaskId(), taskDeadlineRequestDto.getDeadline());
    }

    @Override
    public void updateTaskState(Long taskId, TaskState newState) {
        taskServicePort.updateTaskState(taskId, newState);
    }

    @Override
    public List<TaskModel> getTasksByFilters(Long boardId, TaskState state, TaskPriority priority) {
        return taskServicePort.getTasksByFilters(boardId, state, priority);
    }

    @Override
    public long calculateTaskDuration(Long taskId) {
        return taskServicePort.calculateTaskDuration(taskId);
    }

    @Override
    public void deleteTask(Long taskId) {
        taskServicePort.deleteTask(taskId);
    }

    @Override
    public List<TaskResponseDto> searchTasksByKeyword(String keyword) {
        return taskRequestMapper.toDtoList(taskServicePort.searchTasksByKeyword(keyword));
    }

    @Override
    public String storeFile(MultipartFile file) throws Exception {
        return taskServicePort.storeFile(file);
    }
}
