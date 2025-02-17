package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.ProjectDeadlineRequestDto;
import com.playko.projectManagement.application.dto.request.ProjectRequestDto;

import java.time.LocalDate;

public interface IProjectHandler {
    void createProject(ProjectRequestDto projectRequestDto);
    void updateProjectDeadline(ProjectDeadlineRequestDto projectDeadlineRequestDto);

}
