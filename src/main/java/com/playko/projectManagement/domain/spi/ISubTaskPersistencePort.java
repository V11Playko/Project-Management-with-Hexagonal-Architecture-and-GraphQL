package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.domain.model.SubTaskModel;

import java.util.List;

public interface ISubTaskPersistencePort {
    void addSubTask(Long taskId, SubTaskModel subTaskModel);
    List<SubTaskModel> getSubTasksByTask(Long taskId);
    void updateSubTask(Long subTaskId, SubTaskModel subTaskModel);
    void deleteSubTask(Long subTaskId);
}
