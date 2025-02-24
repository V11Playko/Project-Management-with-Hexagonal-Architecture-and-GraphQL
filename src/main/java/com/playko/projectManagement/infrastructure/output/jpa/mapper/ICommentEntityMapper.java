package com.playko.projectManagement.infrastructure.output.jpa.mapper;

import com.playko.projectManagement.domain.model.CommentModel;
import com.playko.projectManagement.infrastructure.output.jpa.entity.CommentEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICommentEntityMapper {
    CommentModel toModel(CommentEntity commentEntity);
    CommentEntity toEntity(CommentModel commentModel);
    List<CommentModel> toModelList(List<CommentEntity> commentEntities);
}
