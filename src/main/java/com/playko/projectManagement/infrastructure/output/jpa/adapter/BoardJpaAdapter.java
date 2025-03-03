package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.domain.model.BoardModel;
import com.playko.projectManagement.domain.spi.IBoardPersistencePort;
import com.playko.projectManagement.infrastructure.exception.BoardColumnNotFoundException;
import com.playko.projectManagement.infrastructure.exception.BoardNotFoundException;
import com.playko.projectManagement.infrastructure.exception.TaskNotFoundException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.BoardColumnEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.BoardEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.TaskEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IBoardEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IBoardColumnRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IBoardRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ITaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardJpaAdapter implements IBoardPersistencePort {
    private final IBoardRepository boardRepository;
    private final IBoardEntityMapper boardEntityMapper;
    private final ITaskRepository taskRepository;
    private final IBoardColumnRepository boardColumnRepository;

    @Override
    public BoardModel getBoardById(Long boardId) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);
        return boardEntityMapper.toResponse(board);
    }

    @Override
    public void moveTask(Long taskId, Long targetColumnId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        BoardColumnEntity boardColumn = boardColumnRepository.findById(targetColumnId)
                .orElseThrow(BoardColumnNotFoundException::new);

        taskEntity.setBoardColumn(boardColumn);
        taskRepository.save(taskEntity);
    }
}
