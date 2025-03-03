package com.playko.projectManagement.infrastructure.input.rest;


import com.playko.projectManagement.application.dto.response.BoardResponseDto;
import com.playko.projectManagement.application.handler.IBoardHandler;
import com.playko.projectManagement.application.mapper.response.IBoardResponseMapper;
import com.playko.projectManagement.domain.model.BoardModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardRestController {
    private final IBoardHandler boardHandler;
    private final IBoardResponseMapper boardResponseMapper;

    @Operation(summary = "Get board by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Board retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Board not found")
    })
    @GetMapping("/{boardId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable Long boardId) {
        BoardResponseDto board = boardHandler.getBoardById(boardId);
        return ResponseEntity.ok(board);
    }

    @Operation(summary = "Move task to another column")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task moved successfully"),
            @ApiResponse(responseCode = "404", description = "Task or Column not found")
    })
    @PutMapping("/tasks/move")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public ResponseEntity<Void> moveTask(@RequestParam Long taskId, @RequestParam Long targetColumnId) {
        boardHandler.moveTask(taskId, targetColumnId);
        return ResponseEntity.ok().build();
    }
}
