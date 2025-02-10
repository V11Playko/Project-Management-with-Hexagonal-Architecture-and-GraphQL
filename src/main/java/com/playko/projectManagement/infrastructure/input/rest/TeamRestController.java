package com.playko.projectManagement.infrastructure.input.rest;

import com.playko.projectManagement.application.dto.request.AddUserToTeamRequestDto;
import com.playko.projectManagement.application.dto.request.RemoveUserFromTeamRequestDto;
import com.playko.projectManagement.application.dto.request.TeamRequestDto;
import com.playko.projectManagement.application.handler.ITeamHandler;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/team")
@RequiredArgsConstructor
public class TeamRestController {
    private final ITeamHandler teamHandler;

    @Operation(summary = "Add a new Team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Team created", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping("/saveTeam")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> saveTeam(@Valid @RequestBody TeamRequestDto teamRequestDto) {
        teamHandler.saveTeam(teamRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Add a user to the team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User added to the team", content = @Content),
            @ApiResponse(responseCode = "404", description = "Team or User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @PostMapping("/addUserToTeam")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> addUserToTeam(@Valid @RequestBody AddUserToTeamRequestDto addUserToTeamRequestDto) {
        teamHandler.addUserToTeam(addUserToTeamRequestDto.getTeamId(), addUserToTeamRequestDto.getEmailUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Remove a user from the team")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User removed from the team", content = @Content),
            @ApiResponse(responseCode = "404", description = "Team or User not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content)
    })
    @DeleteMapping("/removeUserFromTeam")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> removeUserFromTeam(@Valid @RequestBody RemoveUserFromTeamRequestDto removeUserFromTeamRequestDto) {
        teamHandler.removeUserFromTeam(removeUserFromTeamRequestDto.getTeamId(), removeUserFromTeamRequestDto.getEmailUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
