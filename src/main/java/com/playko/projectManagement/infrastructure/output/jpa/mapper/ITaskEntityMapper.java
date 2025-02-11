package com.playko.projectManagement.infrastructure.output.jpa.mapper;

import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.infrastructure.output.jpa.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITaskEntityMapper {
    @Mapping(source = "project", target = "project.id")
    @Mapping(source = "boardColumn", target = "boardColumn.id")
    @Mapping(source = "assignedUserId", target = "assignedUser.id")
    TaskEntity toEntity(TaskModel model);
}
