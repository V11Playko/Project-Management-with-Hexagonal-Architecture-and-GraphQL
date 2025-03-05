package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.BoardColumnRequestDto;

public interface IBoardColumnHandler {
    void addColumnToBoard(Long boardId, BoardColumnRequestDto columnRequestDto);
}
