package com.playko.projectManagement.infrastructure.input.rest;

import com.playko.projectManagement.application.dto.request.project.ProjectDeadlineRequestDto;
import com.playko.projectManagement.application.dto.request.project.ProjectRequestDto;
import com.playko.projectManagement.application.dto.request.project.UserRestrictionRequestDto;
import com.playko.projectManagement.application.dto.response.ProjectStatsDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectRestController {
    private final IProjectHandler projectHandler;

    @Operation(summary = "Create a new Project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/createProject")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public ResponseEntity<Void> createProject(@Valid @RequestBody ProjectRequestDto projectRequestDto) {
        projectHandler.createProject(projectRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Update project deadline")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project deadline updated"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/updateDeadline")
    public ResponseEntity<Void> updateProjectDeadline(@Valid @RequestBody ProjectDeadlineRequestDto request) {
        projectHandler.updateProjectDeadline(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Archive a completed project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project archived successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "400", description = "Project is not completed"),
            @ApiResponse(responseCode = "403", description = "User is not authorized")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/archive/{projectId}")
    public ResponseEntity<Void> archiveProject(@PathVariable Long projectId) {
        projectHandler.archiveProject(projectId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get project stats")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get project stats"),
            @ApiResponse(responseCode = "404", description = "Stats not found"),
            @ApiResponse(responseCode = "403", description = "User not authorized")
    })
    @PreAuthorize("hasAnyRole('OBSERVER', 'USER')")
    @GetMapping("/stats/{projectId}")
    public ResponseEntity<ProjectStatsDto> getProjectStats(@PathVariable Long projectId) {
        ProjectStatsDto stats = projectHandler.getProjectStats(projectId);
        return ResponseEntity.ok(stats);
    }


    @Operation(summary = "Restrict a user from a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User restricted successfully"),
            @ApiResponse(responseCode = "404", description = "Project or user not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/restrictUser")
    public ResponseEntity<String> restrictUser(@Valid @RequestBody UserRestrictionRequestDto requestDto) {
        projectHandler.restrictUserFromProject(requestDto.getProjectId(), requestDto.getEmail());
        return ResponseEntity.ok("Usuario restringido correctamente.");
    }

    @Operation(summary = "Remove restriction from a user in a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User restriction removed successfully"),
            @ApiResponse(responseCode = "404", description = "Project or user not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/removeRestriction")
    public ResponseEntity<String> removeRestriction(@Valid @RequestBody UserRestrictionRequestDto requestDto) {
        projectHandler.removeUserRestriction(requestDto.getProjectId(), requestDto.getEmail());
        return ResponseEntity.ok("Restricción eliminada correctamente.");
    }


}