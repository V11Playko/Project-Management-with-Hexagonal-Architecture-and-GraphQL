package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.application.dto.request.team.TeamEmailRequestDto;
import com.playko.projectManagement.application.dto.request.team.TeamPerformanceReportDto;
import com.playko.projectManagement.domain.model.TeamModel;

public interface ITeamServicePort {
    void saveTeam(TeamModel teamModel);
    void addUserToTeam(Long teamId, String emailUser);
    void removeUserFromTeam(Long teamId, String emailUser);
    TeamPerformanceReportDto generatePerformanceReport(Long teamId);
    void sendEmailToTeam(TeamEmailRequestDto emailRequest);
}
