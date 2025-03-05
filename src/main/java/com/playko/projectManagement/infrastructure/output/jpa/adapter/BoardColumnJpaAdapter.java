package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.domain.model.BoardColumnModel;
import com.playko.projectManagement.domain.spi.IBoardColumnPersistencePort;
import com.playko.projectManagement.infrastructure.exception.BoardNotFoundException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.BoardColumnEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.BoardEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IBoardColumnEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IBoardColumnRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IBoardRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardColumnJpaAdapter implements IBoardColumnPersistencePort {
    private final IBoardRepository boardRepository;
    private final IBoardColumnRepository boardColumnRepository;
    private final IBoardColumnEntityMapper boardColumnEntityMapper;
    @Override
    public void addColumnToBoard(Long boardId, BoardColumnModel columnModel) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        BoardColumnEntity columnEntity = boardColumnEntityMapper.toEntity(columnModel);
        columnEntity.setBoard(board);

        boardColumnRepository.save(columnEntity);
    }
}
