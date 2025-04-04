package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.team.TeamPerformanceReportDto;
import com.playko.projectManagement.application.dto.request.team.TeamRequestDto;
import com.playko.projectManagement.application.handler.ITeamHandler;
import com.playko.projectManagement.application.mapper.request.ITeamRequestMapper;
import com.playko.projectManagement.domain.api.ITeamServicePort;
import com.playko.projectManagement.domain.model.TeamModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamHandler implements ITeamHandler {
    private final ITeamServicePort teamServicePort;
    private final ITeamRequestMapper teamRequestMapper;
    @Override
    public void saveTeam(TeamRequestDto teamRequestDto) {
        TeamModel teamModel = teamRequestMapper.toTeamRequest(teamRequestDto);
        teamServicePort.saveTeam(teamModel);
    }

    @Override
    public void addUserToTeam(Long teamId, String emailUser) {
        teamServicePort.addUserToTeam(teamId, emailUser);
    }

    @Override
    public void removeUserFromTeam(Long teamId, String emailUser) {
        teamServicePort.removeUserFromTeam(teamId, emailUser);
    }

    @Override
    public TeamPerformanceReportDto generatePerformanceReport(Long teamId) {
        return teamServicePort.generatePerformanceReport(teamId);
    }
}
