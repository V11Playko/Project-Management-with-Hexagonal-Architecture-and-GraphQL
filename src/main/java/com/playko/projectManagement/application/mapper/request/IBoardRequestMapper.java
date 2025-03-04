package com.playko.projectManagement.application.mapper.request;

import com.playko.projectManagement.application.dto.request.BoardRequestDto;
import com.playko.projectManagement.domain.model.BoardModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBoardRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project.id", source = "projectId")
    BoardModel toModel(BoardRequestDto boardRequestDto);
}
