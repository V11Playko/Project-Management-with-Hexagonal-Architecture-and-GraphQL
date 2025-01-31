package com.playko.projectManagement.infrastructure.input.rest;

import com.playko.projectManagement.application.dto.request.UserRequestDto;
import com.playko.projectManagement.application.dto.request.UserUpdateRequestDto;
import com.playko.projectManagement.application.dto.response.UserResponseDto;
import com.playko.projectManagement.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserHandler userHandler;

    @Operation(summary = "Add a new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody UserRequestDto userRequestDto){
        userHandler.saveUser(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved", content = @Content),
            @ApiResponse(responseCode = "204", description = "No users found", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userHandler.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @GetMapping("/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(@PathVariable String email) {
        UserResponseDto user = userHandler.findByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        userHandler.updateUser(id, userUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
