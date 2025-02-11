package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.domain.api.ITaskServicePort;
import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.domain.spi.ITaskPersistencePort;

public class TaskUseCase implements ITaskServicePort {
    private final ITaskPersistencePort taskPersistencePort;

    public TaskUseCase(ITaskPersistencePort taskPersistencePort) {
        this.taskPersistencePort = taskPersistencePort;
    }

    @Override
    public void saveTask(TaskModel taskModel) {
        taskPersistencePort.saveTask(taskModel);
    }
}
