package com.playko.projectManagement.application.mapper.request;

import com.playko.projectManagement.application.dto.request.BoardColumnRequestDto;
import com.playko.projectManagement.domain.model.BoardColumnModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBoardColumnRequestMapper {
    @Mapping(target = "id", ignore = true)
    BoardColumnModel toModel(BoardColumnRequestDto columnDto);
}

