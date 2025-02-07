package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.TeamRequestDto;

public interface ITeamHandler {
    void saveTeam(TeamRequestDto teamRequestDto);
    void addUserToTeam(Long teamId, String emailUser);
    void removeUserFromTeam(Long teamId, String emailUser);
}
