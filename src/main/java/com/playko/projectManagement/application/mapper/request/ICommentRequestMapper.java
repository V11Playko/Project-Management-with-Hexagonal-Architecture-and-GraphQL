package com.playko.projectManagement.application.mapper.request;

import com.playko.projectManagement.application.dto.request.CommentRequestDto;
import com.playko.projectManagement.domain.model.CommentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ICommentRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "task.id", source = "taskId")
    @Mapping(target = "user", ignore = true)
    CommentModel toModel(CommentRequestDto commentRequestDto);
}
