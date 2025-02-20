package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.task.TaskAssignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskDeadlineRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskReassignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskRequestDto;
import com.playko.projectManagement.application.handler.ITaskHandler;
import com.playko.projectManagement.application.mapper.request.ITaskRequestMapper;
import com.playko.projectManagement.domain.api.ITaskServicePort;
import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.shared.enums.TaskState;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskHandler implements ITaskHandler {
    private final ITaskServicePort taskServicePort;
    private final ITaskRequestMapper taskRequestMapper;
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
}
