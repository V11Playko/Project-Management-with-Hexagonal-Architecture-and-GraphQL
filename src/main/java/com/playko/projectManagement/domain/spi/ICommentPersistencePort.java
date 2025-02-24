package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.domain.model.CommentModel;

import java.util.List;

public interface ICommentPersistencePort {
    void addComment(CommentModel commentModel);
    List<CommentModel> getCommentsByTask(Long taskId);

}
