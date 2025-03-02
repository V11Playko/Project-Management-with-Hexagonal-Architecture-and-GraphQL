package com.playko.projectManagement.application.mapper.response;

import com.playko.projectManagement.application.dto.response.BoardResponseDto;
import com.playko.projectManagement.domain.model.BoardColumnModel;
import com.playko.projectManagement.domain.model.BoardModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IBoardResponseMapper {

    @Mapping(target = "name", source = "name")
    @Mapping(target = "columnsName", source = "columns", qualifiedByName = "mapColumnsToNames")
    @Mapping(target = "projectName", source = "project.name")
    BoardResponseDto toResponse(BoardModel boardModel);

    @Named("mapColumnsToNames")
    static List<String> mapColumnsToNames(List<BoardColumnModel> columns) {
        return columns == null ? Collections.emptyList() :
                columns.stream().map(BoardColumnModel::getName).collect(Collectors.toList());
    }
}

