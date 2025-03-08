package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.application.dto.response.ProjectStatsDto;
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

    @Override
    public void updateProjectDeadline(Long projectId, LocalDate deadline) {
        projectPersistencePort.updateProjectDeadline(projectId, deadline);
    }

    @Override
    public void archiveProject(Long projectId) {
        projectPersistencePort.archiveProject(projectId);
    }

    @Override
    public ProjectStatsDto getProjectStats(Long projectId) {
        return projectPersistencePort.getProjectStats(projectId);
    }
}
