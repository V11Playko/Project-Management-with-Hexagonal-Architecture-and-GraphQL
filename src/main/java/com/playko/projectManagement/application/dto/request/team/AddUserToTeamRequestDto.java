package com.playko.projectManagement.application.dto.request.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddUserToTeamRequestDto {
    private Long teamId;
    private String emailUser;
}
