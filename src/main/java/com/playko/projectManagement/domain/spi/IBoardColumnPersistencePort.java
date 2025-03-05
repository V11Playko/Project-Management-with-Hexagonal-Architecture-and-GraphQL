package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.domain.model.BoardColumnModel;

public interface IBoardColumnPersistencePort {
    void addColumnToBoard(Long boardId, BoardColumnModel columnModel);
}
