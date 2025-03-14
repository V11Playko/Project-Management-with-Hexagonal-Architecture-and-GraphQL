package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.project.ProjectDeadlineRequestDto;
import com.playko.projectManagement.application.dto.request.project.ProjectRequestDto;
import com.playko.projectManagement.application.dto.response.ProjectStatsDto;
import com.playko.projectManagement.application.handler.IProjectHandler;
import com.playko.projectManagement.application.mapper.request.IProjectRequestMapper;
import com.playko.projectManagement.domain.api.IProjectServicePort;
import com.playko.projectManagement.domain.model.ProjectModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectHandler implements IProjectHandler {
    private final IProjectServicePort projectServicePort;
    private final IProjectRequestMapper projectRequestMapper;

    @Override
    public void createProject(ProjectRequestDto projectRequestDto) {
        ProjectModel projectModel = projectRequestMapper.toProjectModel(projectRequestDto);
        projectServicePort.createProject(projectModel);
    }

    @Override
    public void updateProjectDeadline(ProjectDeadlineRequestDto projectDto) {
        projectServicePort.updateProjectDeadline(projectDto.getProjectId(), projectDto.getDeadline());
    }

    @Override
    public void archiveProject(Long projectId) {
        projectServicePort.archiveProject(projectId);
    }

    @Override
    public ProjectStatsDto getProjectStats(Long projectId) {
        return projectServicePort.getProjectStats(projectId);
    }
}