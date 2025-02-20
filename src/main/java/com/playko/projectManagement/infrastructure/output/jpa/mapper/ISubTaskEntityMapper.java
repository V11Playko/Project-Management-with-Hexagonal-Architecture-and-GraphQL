package com.playko.projectManagement.infrastructure.output.jpa.mapper;

import com.playko.projectManagement.domain.model.SubTaskModel;
import com.playko.projectManagement.infrastructure.output.jpa.entity.SubTaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ISubTaskEntityMapper {

    SubTaskEntity toEntity(SubTaskModel subTaskModel);
    List<SubTaskModel> toModelList(List<SubTaskEntity> subTaskEntities);
}
