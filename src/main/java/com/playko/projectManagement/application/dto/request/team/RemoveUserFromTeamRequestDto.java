package com.playko.projectManagement.application.dto.request.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RemoveUserFromTeamRequestDto {
    private Long teamId;
    private String emailUser;
}
