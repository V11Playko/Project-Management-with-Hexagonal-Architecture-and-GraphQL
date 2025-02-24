package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.CommentRequestDto;
import com.playko.projectManagement.application.dto.response.CommentResponseDto;
import com.playko.projectManagement.application.handler.ICommentHandler;
import com.playko.projectManagement.application.mapper.request.ICommentRequestMapper;
import com.playko.projectManagement.domain.api.ICommentServicePort;
import com.playko.projectManagement.domain.model.CommentModel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CommentHandler implements ICommentHandler {
    private final ICommentServicePort commentServicePort;
    private final ICommentRequestMapper commentRequestMapper;

    @Override
    public void addComment(CommentRequestDto commentRequestDto) {
        CommentModel commentModel = commentRequestMapper.toModel(commentRequestDto);
        commentServicePort.addComment(commentModel);
    }

    @Override
    public List<CommentResponseDto> getCommentsByTask(Long taskId) {
        return commentServicePort.getCommentsByTask(taskId)
                .stream()
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedAt(),
                        comment.getUser().getName()))
                .collect(Collectors.toList());
    }
}
