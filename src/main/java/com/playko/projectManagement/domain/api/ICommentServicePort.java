package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.domain.model.CommentModel;

import java.util.List;

public interface ICommentServicePort {
    void addComment(CommentModel commentModel);
    List<CommentModel> getCommentsByTask(Long taskId);
}
