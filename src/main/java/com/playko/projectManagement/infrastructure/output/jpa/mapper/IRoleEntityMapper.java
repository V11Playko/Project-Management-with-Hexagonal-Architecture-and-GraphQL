package com.playko.projectManagement.infrastructure.output.jpa.mapper;

import com.playko.projectManagement.domain.model.RoleModel;
import com.playko.projectManagement.infrastructure.output.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRoleEntityMapper {
    RoleModel toRole(RoleEntity roleEntity);
    List<RoleModel> toRoleList(List<RoleEntity> roleEntityList);
}
