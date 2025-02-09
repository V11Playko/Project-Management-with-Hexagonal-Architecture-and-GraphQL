package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.domain.model.ProjectModel;
import com.playko.projectManagement.domain.spi.IProjectPersistencePort;
import com.playko.projectManagement.infrastructure.output.jpa.entity.ProjectEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IProjectEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IProjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProjectJpaAdapter implements IProjectPersistencePort {
    private final IProjectRepository projectRepository;
    private final IProjectEntityMapper projectEntityMapper;

    @Override
    public void createProject(ProjectModel projectModel) {
        ProjectEntity projectEntity = projectEntityMapper.toEntity(projectModel);
        projectRepository.save(projectEntity);
    }
}
