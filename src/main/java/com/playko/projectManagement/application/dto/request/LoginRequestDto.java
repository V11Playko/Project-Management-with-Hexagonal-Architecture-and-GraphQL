package com.playko.projectManagement.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Field 'email' must be a valid email direction. Enter the format name@example.com")
    private String email;
    @NotBlank(message = "Password cannot be null")
    @Size(min = 4, max = 255, message = "Password must be between 1 and 150 characters")
    private String password;
}