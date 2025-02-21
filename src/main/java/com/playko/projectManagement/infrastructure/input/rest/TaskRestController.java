package com.playko.projectManagement.infrastructure.input.rest;

import com.playko.projectManagement.application.dto.request.task.TaskAssignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskDeadlineRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskReassignmentRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskRequestDto;
import com.playko.projectManagement.application.dto.request.task.TaskStateUpdateRequestDto;
import com.playko.projectManagement.application.dto.response.TaskResponseDto;
import com.playko.projectManagement.application.handler.ITaskHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskRestController {
    private final ITaskHandler taskHandler;

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @GetMapping("/myTasks")
    public ResponseEntity<List<TaskResponseDto>> getTasksByUserEmail() {
        List<TaskResponseDto> tasks = taskHandler.getTasksByUserEmail();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(summary = "Save a new task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "task created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/saveTask")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> saveTask(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        taskHandler.saveTask(taskRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Assign a task to a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task assigned successfully"),
            @ApiResponse(responseCode = "404", description = "Task or User not found"),
            @ApiResponse(responseCode = "403", description = "User is not authorized")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/assignTask")
    public ResponseEntity<Void> assignTaskToUser(@Valid @RequestBody TaskAssignmentRequestDto request) {
        taskHandler.assignTaskToUser(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Reassign a task to another user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task reassigned successfully"),
            @ApiResponse(responseCode = "404", description = "Task or User not found"),
            @ApiResponse(responseCode = "403", description = "User is not authorized")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/reassignTask")
    public ResponseEntity<Void> reassignTask(@Valid @RequestBody TaskReassignmentRequestDto request) {
        taskHandler.reassignTask(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update task deadline")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deadline updated"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/updateDeadline")
    public ResponseEntity<Void> updateTaskDeadline(@Valid @RequestBody TaskDeadlineRequestDto request) {
        taskHandler.updateTaskDeadline(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PutMapping("/{taskId}/updateState")
    public ResponseEntity<Void> updateTaskState(@PathVariable Long taskId, @RequestBody TaskStateUpdateRequestDto request) {
        taskHandler.updateTaskState(taskId, request.getNewState());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
