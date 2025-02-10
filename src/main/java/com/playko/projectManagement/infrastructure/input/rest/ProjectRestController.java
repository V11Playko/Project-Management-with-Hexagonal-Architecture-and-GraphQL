package com.playko.projectManagement.infrastructure.input.rest;

import com.playko.projectManagement.application.dto.request.ProjectRequestDto;
import com.playko.projectManagement.application.handler.IProjectHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectRestController {
    private final IProjectHandler projectHandler;

    @Operation(summary = "Create a new Project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public ResponseEntity<Void> createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {
        projectHandler.createProject(projectRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}