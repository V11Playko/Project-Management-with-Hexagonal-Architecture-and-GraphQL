package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.domain.model.TeamModel;

public interface ITeamPersistencePort {
    void saveTeam(TeamModel teamModel);
    void addUserToTeam(Long teamId, String emailUser);
}
