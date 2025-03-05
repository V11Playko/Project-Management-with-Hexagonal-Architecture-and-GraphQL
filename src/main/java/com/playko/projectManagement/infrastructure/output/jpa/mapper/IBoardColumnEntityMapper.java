package com.playko.projectManagement.infrastructure.output.jpa.mapper;

import com.playko.projectManagement.domain.model.BoardColumnModel;
import com.playko.projectManagement.infrastructure.output.jpa.entity.BoardColumnEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBoardColumnEntityMapper {
    BoardColumnEntity toEntity(BoardColumnModel columnModel);
    BoardColumnModel toModel(BoardColumnEntity columnEntity);
}
