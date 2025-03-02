package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.response.BoardResponseDto;

public interface IBoardHandler {
    BoardResponseDto getBoardById(Long boardId);
}
