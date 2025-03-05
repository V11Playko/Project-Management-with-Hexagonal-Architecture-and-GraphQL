package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.domain.api.IBoardColumnServicePort;
import com.playko.projectManagement.domain.model.BoardColumnModel;
import com.playko.projectManagement.domain.spi.IBoardColumnPersistencePort;

public class BoardColumnUseCase implements IBoardColumnServicePort {
    private final IBoardColumnPersistencePort boardColumnPersistencePort;

    public BoardColumnUseCase(IBoardColumnPersistencePort boardColumnPersistencePort) {
        this.boardColumnPersistencePort = boardColumnPersistencePort;
    }

    @Override
    public void addColumnToBoard(Long boardId, BoardColumnModel columnModel) {
        boardColumnPersistencePort.addColumnToBoard(boardId, columnModel);
    }
}
