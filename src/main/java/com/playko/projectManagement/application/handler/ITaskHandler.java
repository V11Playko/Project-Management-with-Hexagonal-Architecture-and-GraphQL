package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.task.TaskAssignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskReassignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskRequestDto;

public interface ITaskHandler {
    void saveTask(TaskRequestDto taskRequestDto);
    void assignTaskToUser(TaskAssignmentRequestDto taskAssignmentRequestDto);
    void reassignTask(TaskReassignmentRequestDto taskReassignmentRequestDto);
}
