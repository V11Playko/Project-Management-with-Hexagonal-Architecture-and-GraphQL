package com.playko.projectManagement.application.mapper.request;

import com.playko.projectManagement.application.dto.request.SubTaskRequestDto;
import com.playko.projectManagement.application.dto.response.SubTaskResponseDto;
import com.playko.projectManagement.domain.model.SubTaskModel;
import com.playko.projectManagement.domain.model.TaskModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ISubTaskRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task.title", source = "task")
    @Mapping(target = "state", source = "state")
    SubTaskModel toModel(SubTaskRequestDto subTaskRequestDto);

    @Mapping(target = "task", source = "task", qualifiedByName = "mapTaskModelToTitle")
    SubTaskResponseDto toResponse(SubTaskModel subTaskModel);
    List<SubTaskResponseDto> toResponseList(List<SubTaskModel> subTaskModels);

    @Named("mapTaskModelToTitle")
    default String mapTaskModelToTitle(TaskModel task) {
        return (task != null) ? task.getTitle() : null;
    }

}