package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.domain.api.ITeamServicePort;
import com.playko.projectManagement.domain.model.TeamModel;
import com.playko.projectManagement.domain.spi.ITeamPersistencePort;
import com.playko.projectManagement.shared.enums.TeamState;

import java.time.LocalDate;

public class TeamUseCase implements ITeamServicePort {
    private final ITeamPersistencePort teamPersistencePort;

    public TeamUseCase(ITeamPersistencePort teamPersistencePort) {
        this.teamPersistencePort = teamPersistencePort;
    }

    @Override
    public void saveTeam(TeamModel teamModel) {
        teamModel.setCreationDate(LocalDate.now());
        teamModel.setState(TeamState.ACTIVE);
        teamPersistencePort.saveTeam(teamModel);
    }

    @Override
    public void addUserToTeam(Long teamId, String emailUser) {
        teamPersistencePort.addUserToTeam(teamId, emailUser);
    }
}
