package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.CommentRequestDto;
import com.playko.projectManagement.application.dto.response.CommentResponseDto;

import java.util.List;

public interface ICommentHandler {
    void addComment(CommentRequestDto commentRequestDto);
    List<CommentResponseDto> getCommentsByTask(Long taskId);
}
