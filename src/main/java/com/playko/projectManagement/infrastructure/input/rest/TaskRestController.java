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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @Operation(summary = "Update task state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task state updated"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PreAuthorize("hasRole('CONTRIBUTOR')")
    @PutMapping("/updateState/{taskId}")
    public ResponseEntity<Void> updateTaskState(@PathVariable Long taskId, @RequestBody TaskStateUpdateRequestDto request) {
        taskHandler.updateTaskState(taskId, request.getNewState());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get task duration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task duration retrieved"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PreAuthorize("hasAnyRole('MANAGER', 'CONTRIBUTOR', 'USER')")
    @GetMapping("/duration/{taskId}")
    public ResponseEntity<String> getTaskDuration(@PathVariable Long taskId) {
        long daysTaken = taskHandler.calculateTaskDuration(taskId);
        String message = "Días empleados para completar la tarea: " + daysTaken;
        return ResponseEntity.ok(message);
    }

    @Operation(summary = "Delete task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @DeleteMapping("/deleteTask/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskHandler.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Upload a picture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "No file provided"),
            @ApiResponse(responseCode = "500", description = "Error uploading file")
    })
    @PostMapping("/picture")
    public ResponseEntity<String> uploadPic(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file provided");
        }
        try {
            String filePath = taskHandler.storeFile(file);
            return ResponseEntity.ok("File uploaded successfully: " + filePath);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }
}
