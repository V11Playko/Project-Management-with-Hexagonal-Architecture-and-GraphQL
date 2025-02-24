package com.playko.projectManagement.application.mapper.request;

import com.playko.projectManagement.application.dto.request.CommentRequestDto;
import com.playko.projectManagement.domain.model.CommentModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICommentRequestMapper {
    CommentModel toModel(CommentRequestDto commentRequestDto);
}
