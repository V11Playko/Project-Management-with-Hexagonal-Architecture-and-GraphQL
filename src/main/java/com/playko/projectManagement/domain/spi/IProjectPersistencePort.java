package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.domain.model.ProjectModel;

public interface IProjectPersistencePort {
    void createProject(ProjectModel projectModel);
}
