package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.domain.api.ICommentServicePort;
import com.playko.projectManagement.domain.model.CommentModel;
import com.playko.projectManagement.domain.spi.ICommentPersistencePort;

import java.util.List;

public class CommentUseCase implements ICommentServicePort {
    private final ICommentPersistencePort commentPersistencePort;

    public CommentUseCase(ICommentPersistencePort commentPersistencePort) {
        this.commentPersistencePort = commentPersistencePort;
    }

    @Override
    public void addComment(CommentModel commentModel) {
        commentPersistencePort.addComment(commentModel);
    }

    @Override
    public List<CommentModel> getCommentsByTask(Long taskId) {
        return commentPersistencePort.getCommentsByTask(taskId);
    }
}
