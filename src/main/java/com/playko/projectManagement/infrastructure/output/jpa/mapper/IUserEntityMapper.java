package com.playko.projectManagement.infrastructure.output.jpa.mapper;

import com.playko.projectManagement.domain.model.UserModel;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUserEntityMapper {
    @Mapping(target = "team.name", source = "team")
    UserEntity toEntity(UserModel userModel);
    @Mapping(target = "team", source = "team.name")
    UserModel toUserModel(UserEntity userEntity);

    List<UserModel> toUserModelList(List<UserEntity> userEntities);
}
