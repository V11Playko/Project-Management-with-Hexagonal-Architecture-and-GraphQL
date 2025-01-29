package com.playko.projectManagement.application.mapper.response;

import com.playko.projectManagement.application.dto.response.RoleResponseDto;
import com.playko.projectManagement.domain.model.RoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleResponseMapper {
    List<RoleResponseDto> toResponseList(List<RoleModel> roleList);

    RoleResponseDto toResponse(RoleModel role);
}
