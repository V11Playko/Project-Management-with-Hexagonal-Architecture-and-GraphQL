package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.domain.model.ProjectModel;

import java.time.LocalDate;

public interface IProjectPersistencePort {
    void createProject(ProjectModel projectModel);
    void updateProjectDeadline(Long projectId, LocalDate deadline);

}
