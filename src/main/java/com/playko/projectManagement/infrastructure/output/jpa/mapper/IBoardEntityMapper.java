package com.playko.projectManagement.infrastructure.output.jpa.mapper;

import com.playko.projectManagement.domain.model.BoardModel;
import com.playko.projectManagement.infrastructure.output.jpa.entity.BoardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBoardEntityMapper {
    BoardEntity toEntity(BoardModel boardModel);
    BoardModel toResponse(BoardEntity board);
}
