package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.domain.model.SubTaskModel;

import java.util.List;

public interface ISubTaskServicePort {
    void addSubTask(Long taskId, SubTaskModel subTaskModel);
    List<SubTaskModel> getSubTasksByTask(Long taskId);
    void updateSubTask(Long subTaskId, SubTaskModel subTaskModel);
    void deleteSubTask(Long subTaskId);
}
