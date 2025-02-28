package com.playko.projectManagement.application.mapper.request;

import com.playko.projectManagement.application.dto.request.task.TaskAssignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskRequestDto;
import com.playko.projectManagement.application.dto.response.TaskResponseDto;
import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

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
    @Mapping(target = "assignedUserId.id", source = "userId")
    TaskModel taskAssignmentToModel(TaskAssignmentRequestDto assignmentRequestDto);

    @Mapping(target = "assignedUserId", source = "assignedUserId", qualifiedByName = "mapUserModelToLong")
    TaskResponseDto toDto(TaskModel taskModel);

    List<TaskResponseDto> toDtoList(List<TaskModel> taskModels);

    @Named("mapUserModelToLong")
    default Long mapUserModelToLong(UserModel user) {
        return (user != null) ? user.getId() : null;
    }
}
