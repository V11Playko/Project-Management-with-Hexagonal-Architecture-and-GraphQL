package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.domain.model.BoardModel;

public interface IBoardPersistencePort {
    BoardModel getBoardById(Long boardId);
}
