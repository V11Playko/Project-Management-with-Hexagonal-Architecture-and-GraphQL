package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.ProjectRequestDto;

public interface IProjectHandler {
    void createProject(ProjectRequestDto projectRequestDto);
}
