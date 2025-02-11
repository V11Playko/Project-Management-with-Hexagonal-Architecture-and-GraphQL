package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.domain.model.TaskModel;

public interface ITaskPersistencePort {
    void saveTask(TaskModel taskModel);
}
