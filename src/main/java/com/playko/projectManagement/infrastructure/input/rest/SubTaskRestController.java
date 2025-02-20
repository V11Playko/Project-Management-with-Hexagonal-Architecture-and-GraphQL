package com.playko.projectManagement.infrastructure.input.rest;

import com.playko.projectManagement.application.dto.request.SubTaskRequestDto;
import com.playko.projectManagement.application.dto.response.SubTaskResponseDto;
import com.playko.projectManagement.application.handler.ISubTaskHandler;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subtasks")
@RequiredArgsConstructor
public class SubTaskRestController {
    private final ISubTaskHandler subTaskHandler;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/add/{taskId}")
    public ResponseEntity<Void> addSubTask(@PathVariable Long taskId, @RequestBody SubTaskRequestDto request) {
        subTaskHandler.addSubTask(taskId, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<List<SubTaskResponseDto>> getSubTasksByTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(subTaskHandler.getSubTasksByTask(taskId));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/update/{subTaskId}")
    public ResponseEntity<Void> updateSubTask(@PathVariable Long subTaskId, @RequestBody SubTaskRequestDto request) {
        subTaskHandler.updateSubTask(subTaskId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/delete/{subTaskId}")
    public ResponseEntity<Void> deleteSubTask(@PathVariable Long subTaskId) {
        subTaskHandler.deleteSubTask(subTaskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
