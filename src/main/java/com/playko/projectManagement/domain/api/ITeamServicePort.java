package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.domain.model.TeamModel;

public interface ITeamServicePort {
    void saveTeam(TeamModel teamModel);
    void addUserToTeam(Long teamId, String emailUser);
}
