package com.playko.projectManagement.infrastructure.input.rest;


import com.playko.projectManagement.application.dto.request.BoardRequestDto;
import com.playko.projectManagement.application.dto.request.task.MoveTaskRequestDto;
import com.playko.projectManagement.application.dto.response.BoardResponseDto;
import com.playko.projectManagement.application.handler.IBoardHandler;
import com.playko.projectManagement.application.mapper.response.IBoardResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<Void> moveTask(@Valid @RequestBody MoveTaskRequestDto requestDto) {
        boardHandler.moveTask(requestDto.getTaskId(), requestDto.getTargetColumnId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Save a new board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Board created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> saveBoard(@RequestBody BoardRequestDto boardRequestDto) {
        boardHandler.saveBoard(boardRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
