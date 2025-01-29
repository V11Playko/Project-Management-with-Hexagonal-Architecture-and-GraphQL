package com.playko.projectManagement.infrastructure.input.rest;

import com.playko.projectManagement.application.dto.request.UserRequestDto;
import com.playko.projectManagement.application.handler.IUserHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
