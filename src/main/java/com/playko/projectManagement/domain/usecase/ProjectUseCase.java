package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.domain.api.IProjectServicePort;
import com.playko.projectManagement.domain.model.ProjectModel;
import com.playko.projectManagement.domain.spi.IProjectPersistencePort;
import com.playko.projectManagement.shared.enums.ProjectState;

import java.time.LocalDate;

public class ProjectUseCase implements IProjectServicePort {
    private final IProjectPersistencePort projectPersistencePort;

    public ProjectUseCase(IProjectPersistencePort projectPersistencePort) {
        this.projectPersistencePort = projectPersistencePort;
    }

    @Override
    public void createProject(ProjectModel projectModel) {
        projectModel.setCreationDate(LocalDate.now());
        projectModel.setState(ProjectState.ACTIVE);
        projectPersistencePort.createProject(projectModel);
    }
}
