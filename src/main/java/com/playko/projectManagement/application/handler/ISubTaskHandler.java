package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.SubTaskRequestDto;
import com.playko.projectManagement.application.dto.response.SubTaskResponseDto;

import java.util.List;

public interface ISubTaskHandler {
    void addSubTask(Long taskId, SubTaskRequestDto subTaskRequestDto);
    List<SubTaskResponseDto> getSubTasksByTask(Long taskId);
    void updateSubTask(Long subTaskId, SubTaskRequestDto subTaskRequestDto);
    void deleteSubTask(Long subTaskId);
}
