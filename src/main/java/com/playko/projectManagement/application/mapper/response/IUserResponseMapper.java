package com.playko.projectManagement.application.mapper.response;

import com.playko.projectManagement.application.dto.response.UserResponseDto;
import com.playko.projectManagement.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserResponseMapper {

    @Mapping(target = "nameRole", source = "roleModel.name")
    @Mapping(target = "descriptionRole", source = "roleModel.description")
    @Mapping(target = "nameTeam", source = "team.name")
    UserResponseDto toResponse(UserModel userModel);
    List<UserResponseDto> toResponseList(List<UserModel> userModels);
}
