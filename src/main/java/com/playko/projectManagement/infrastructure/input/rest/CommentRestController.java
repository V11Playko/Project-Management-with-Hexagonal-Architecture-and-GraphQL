package com.playko.projectManagement.infrastructure.input.rest;

import com.playko.projectManagement.application.dto.request.CommentRequestDto;
import com.playko.projectManagement.application.dto.response.CommentResponseDto;
import com.playko.projectManagement.application.handler.ICommentHandler;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentRestController {
    private final ICommentHandler commentHandler;

    @Operation(summary = "Add a comment to a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment added successfully"),
            @ApiResponse(responseCode = "404", description = "Task or User not found")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public ResponseEntity<Void> addComment(@Valid @RequestBody CommentRequestDto request) {
        commentHandler.addComment(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all comments for a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByTask(@PathVariable Long taskId) {
        return new ResponseEntity<>(commentHandler.getCommentsByTask(taskId), HttpStatus.OK);
    }
}
