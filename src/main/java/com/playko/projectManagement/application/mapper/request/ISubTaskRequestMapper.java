package com.playko.projectManagement.application.mapper.request;

import com.playko.projectManagement.application.dto.request.SubTaskRequestDto;
import com.playko.projectManagement.application.dto.response.SubTaskResponseDto;
import com.playko.projectManagement.domain.model.SubTaskModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ISubTaskRequestMapper {
    SubTaskModel toModel(SubTaskRequestDto subTaskRequestDto);
    List<SubTaskResponseDto> toResponseList(List<SubTaskModel> subTaskModels);
}