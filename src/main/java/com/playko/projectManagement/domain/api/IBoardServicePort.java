package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.domain.model.BoardModel;

public interface IBoardServicePort {
    BoardModel getBoardById(Long boardId);
}
