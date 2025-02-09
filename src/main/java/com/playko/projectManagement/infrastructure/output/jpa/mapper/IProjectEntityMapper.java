package com.playko.projectManagement.infrastructure.output.jpa.mapper;

import com.playko.projectManagement.domain.model.ProjectModel;
import com.playko.projectManagement.infrastructure.output.jpa.entity.ProjectEntity;

public interface IProjectEntityMapper {
    ProjectEntity toEntity(ProjectModel projectModel);
    ProjectModel toModel(ProjectEntity projectEntity);
}
