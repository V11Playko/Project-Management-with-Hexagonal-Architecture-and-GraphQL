package com.playko.projectManagement.infrastructure.input.rest;

import com.playko.projectManagement.application.dto.request.BoardColumnRequestDto;
import com.playko.projectManagement.application.handler.IBoardColumnHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boardColumns")
@RequiredArgsConstructor
public class BoardColumnRestController {

    private final IBoardColumnHandler boardColumnHandler;

    @Operation(summary = "Add a new column to a board")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Column added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "403", description = "User not authorized"),
            @ApiResponse(responseCode = "404", description = "Board not found")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/add/{boardId}")
    public ResponseEntity<Void> addColumnToBoard(@PathVariable Long boardId,
                                                 @RequestBody BoardColumnRequestDto columnDto) {
        boardColumnHandler.addColumnToBoard(boardId, columnDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
