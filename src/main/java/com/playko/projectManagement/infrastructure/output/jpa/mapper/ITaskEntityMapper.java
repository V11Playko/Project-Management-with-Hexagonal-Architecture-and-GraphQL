package com.playko.projectManagement.infrastructure.output.jpa.mapper;

import com.playko.projectManagement.application.dto.request.task.TaskRequestDto;
import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.infrastructure.output.jpa.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITaskEntityMapper {
    @Mapping(source = "project.id", target = "project.id")
    @Mapping(source = "boardColumn.id", target = "boardColumn.id")
    @Mapping(source = "assignedUserId.id", target = "assignedUser.id")
    TaskEntity toEntity(TaskModel model);

    List<TaskModel> toDtoList(List<TaskEntity> taskEntities);


    @Mapping(target = "project.id", source = "project.id")
    @Mapping(target = "boardColumn.id", source = "boardColumn.id")
    @Mapping(target = "assignedUserId.id", source = "assignedUser.id")
    TaskModel toModel(TaskEntity taskEntity);
}
