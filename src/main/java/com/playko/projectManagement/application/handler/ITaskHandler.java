package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.TaskRequestDto;

public interface ITaskHandler {
    void saveTask(TaskRequestDto taskRequestDto);
}
