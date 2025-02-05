package com.playko.projectManagement.infrastructure.output.jpa.mapper;

import com.playko.projectManagement.domain.model.TeamModel;
import com.playko.projectManagement.infrastructure.output.jpa.entity.TeamEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITeamEntityMapper {
    TeamEntity toEntity(TeamModel teamModel);
}
