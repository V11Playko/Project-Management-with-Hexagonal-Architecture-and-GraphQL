package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.project.ProjectDeadlineRequestDto;
import com.playko.projectManagement.application.dto.request.project.ProjectRequestDto;
import com.playko.projectManagement.application.dto.response.ProjectStatsDto;

public interface IProjectHandler {
    void createProject(ProjectRequestDto projectRequestDto);
    void updateProjectDeadline(ProjectDeadlineRequestDto projectDeadlineRequestDto);
    void archiveProject(Long projectId);
    ProjectStatsDto getProjectStats(Long projectId);
    void restrictUserFromProject(Long projectId, String email);
    void removeUserRestriction(Long projectId, String email);
}
