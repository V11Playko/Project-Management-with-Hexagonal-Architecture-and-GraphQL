package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.application.mapper.request.ITeamRequestMapper;
import com.playko.projectManagement.domain.model.TeamModel;
import com.playko.projectManagement.domain.spi.ITeamPersistencePort;
import com.playko.projectManagement.infrastructure.exception.TeamNotFoundException;
import com.playko.projectManagement.infrastructure.exception.UserNotFoundException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.TeamEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.ITeamEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ITeamRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

@RequiredArgsConstructor
public class TeamJpaAdapter implements ITeamPersistencePort {
    private final ITeamRepository teamRepository;
    private final ITeamEntityMapper teamEntityMapper;
    private final IUserRepository userRepository;
    @Override
    public void saveTeam(TeamModel teamModel) {
        TeamEntity teamEntity = teamEntityMapper.toEntity(teamModel);
        teamRepository.save(teamEntity);
    }

    @Override
    public void addUserToTeam(Long teamId, String emailUser) {
        TeamEntity teamEntity = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        UserEntity userEntity = userRepository.findByEmail(emailUser).orElseThrow(UserNotFoundException::new);

        teamEntity.setUsers(Collections.singletonList(userEntity));

        teamRepository.save(teamEntity);
    }
}
