package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.SubTaskRequestDto;
import com.playko.projectManagement.application.dto.response.SubTaskResponseDto;
import com.playko.projectManagement.application.handler.ISubTaskHandler;
import com.playko.projectManagement.application.mapper.request.ISubTaskRequestMapper;
import com.playko.projectManagement.domain.api.ISubTaskServicePort;
import com.playko.projectManagement.domain.model.SubTaskModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SubTaskHandler implements ISubTaskHandler {
    private final ISubTaskServicePort subTaskServicePort;
    private final ISubTaskRequestMapper subTaskRequestMapper;

    @Override
    public void addSubTask(Long taskId, SubTaskRequestDto subTaskRequestDto) {
        SubTaskModel subTaskModel = subTaskRequestMapper.toModel(subTaskRequestDto);
        subTaskServicePort.addSubTask(taskId, subTaskModel);
    }

    @Override
    public List<SubTaskResponseDto> getSubTasksByTask(Long taskId) {
        return subTaskRequestMapper.toResponseList(subTaskServicePort.getSubTasksByTask(taskId));
    }

    @Override
    public void updateSubTask(Long subTaskId, SubTaskRequestDto subTaskRequestDto) {
        SubTaskModel subTaskModel = subTaskRequestMapper.toModel(subTaskRequestDto);
        subTaskServicePort.updateSubTask(subTaskId, subTaskModel);
    }

    @Override
    public void deleteSubTask(Long subTaskId) {
        subTaskServicePort.deleteSubTask(subTaskId);
    }
}
