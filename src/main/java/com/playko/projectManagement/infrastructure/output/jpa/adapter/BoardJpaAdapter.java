package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.domain.model.BoardModel;
import com.playko.projectManagement.domain.spi.IBoardPersistencePort;
import com.playko.projectManagement.infrastructure.exception.BoardColumnNotFoundException;
import com.playko.projectManagement.infrastructure.exception.BoardNotFoundException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.BoardEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IBoardEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IBoardRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class BoardJpaAdapter implements IBoardPersistencePort {
    private final IBoardRepository boardRepository;
    private final IBoardEntityMapper boardEntityMapper;
    @Override
    public BoardModel getBoardById(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        return boardEntityMapper.toResponse(board);
    }
}
