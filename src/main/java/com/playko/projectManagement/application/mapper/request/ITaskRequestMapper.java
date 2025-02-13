package com.playko.projectManagement.application.mapper.request;

import com.playko.projectManagement.application.dto.request.TaskAssignmentRequestDto;
import com.playko.projectManagement.application.dto.request.TaskRequestDto;
import com.playko.projectManagement.domain.model.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITaskRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project.id", source = "projectId")
    @Mapping(target = "boardColumn.id", source = "boardColumnId")
    @Mapping(target = "assignedUserId.id", source = "assignedUserId")
    TaskModel toModel(TaskRequestDto taskRequestDto);

    @Mapping(target = "id", source = "taskId")
    @Mapping(target = "assignedUserId", source = "userId")
    TaskModel taskAssignmentToModel(TaskAssignmentRequestDto assignmentRequestDto);
}
