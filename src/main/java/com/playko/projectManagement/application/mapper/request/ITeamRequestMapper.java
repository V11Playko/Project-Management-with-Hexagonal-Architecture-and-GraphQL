package com.playko.projectManagement.application.mapper.request;

import com.playko.projectManagement.application.dto.request.TeamRequestDto;
import com.playko.projectManagement.domain.model.TeamModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITeamRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "users", ignore = true)
    TeamModel toTeamRequest(TeamRequestDto teamRequestDto);
}
