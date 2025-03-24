package com.playko.projectManagement.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String name;
    private String surname;
    private String dniNumber;
    private String phone;
    private String email;
    private String password;
    private String nameRole;
    private String descriptionRole;
    private String nameTeam;
}
