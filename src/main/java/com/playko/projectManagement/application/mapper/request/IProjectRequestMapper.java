package com.playko.projectManagement.application.mapper.request;

import com.playko.projectManagement.application.dto.request.ProjectRequestDto;
import com.playko.projectManagement.domain.model.ProjectModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IProjectRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "state", constant = "ACTIVE")
    ProjectModel toProjectModel(ProjectRequestDto projectRequestDto);
}
