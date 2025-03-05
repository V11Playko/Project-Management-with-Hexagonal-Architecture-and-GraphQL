package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.BoardColumnRequestDto;
import com.playko.projectManagement.application.dto.request.BoardRequestDto;
import com.playko.projectManagement.application.dto.response.BoardResponseDto;
import com.playko.projectManagement.application.handler.IBoardColumnHandler;
import com.playko.projectManagement.application.handler.IBoardHandler;
import com.playko.projectManagement.application.mapper.request.IBoardColumnRequestMapper;
import com.playko.projectManagement.domain.api.IBoardColumnServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardColumnHandler implements IBoardColumnHandler {

    private final IBoardColumnServicePort boardColumnServicePort;
    private final IBoardColumnRequestMapper boardColumnRequestMapper;

    @Override
    public void addColumnToBoard(Long boardId, BoardColumnRequestDto columnRequestDto) {
        boardColumnServicePort.addColumnToBoard(boardId, boardColumnRequestMapper.toModel(columnRequestDto));
    }
}
