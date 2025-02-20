package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.domain.api.ISubTaskServicePort;
import com.playko.projectManagement.domain.model.SubTaskModel;
import com.playko.projectManagement.domain.spi.ISubTaskPersistencePort;

import java.util.List;

public class SubTaskUseCase implements ISubTaskServicePort {
    private final ISubTaskPersistencePort subTaskPersistencePort;

    public SubTaskUseCase(ISubTaskPersistencePort subTaskPersistencePort) {
        this.subTaskPersistencePort = subTaskPersistencePort;
    }

    @Override
    public void addSubTask(Long taskId, SubTaskModel subTaskModel) {
        subTaskPersistencePort.addSubTask(taskId, subTaskModel);
    }

    @Override
    public List<SubTaskModel> getSubTasksByTask(Long taskId) {
        return subTaskPersistencePort.getSubTasksByTask(taskId);
    }

    @Override
    public void updateSubTask(Long subTaskId, SubTaskModel subTaskModel) {
        subTaskPersistencePort.updateSubTask(subTaskId, subTaskModel);
    }

    @Override
    public void deleteSubTask(Long subTaskId) {
        subTaskPersistencePort.deleteSubTask(subTaskId );
    }
}
