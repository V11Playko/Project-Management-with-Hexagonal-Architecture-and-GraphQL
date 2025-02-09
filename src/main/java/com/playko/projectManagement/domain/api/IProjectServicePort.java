package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.domain.model.ProjectModel;

public interface IProjectServicePort {
    void createProject(ProjectModel projectModel);
}
