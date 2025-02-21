package com.playko.projectManagement.application.mapper.request;

import com.playko.projectManagement.application.dto.request.task.TaskAssignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskRequestDto;
import com.playko.projectManagement.application.dto.response.TaskResponseDto;
import com.playko.projectManagement.domain.model.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

    List<TaskResponseDto> toDtoList(List<TaskModel> taskModels);
}
