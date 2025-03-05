package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.domain.model.BoardColumnModel;

public interface IBoardColumnServicePort {
    void addColumnToBoard(Long boardId, BoardColumnModel columnModel);

}
