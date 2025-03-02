package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.application.dto.request.EmailRequestDto;
import com.playko.projectManagement.application.handler.IEmailHandler;
import com.playko.projectManagement.application.mapper.request.ITeamRequestMapper;
import com.playko.projectManagement.domain.model.TeamModel;
import com.playko.projectManagement.domain.spi.ITeamPersistencePort;
import com.playko.projectManagement.infrastructure.exception.TeamNotFoundException;
import com.playko.projectManagement.infrastructure.exception.UserNotFoundException;
import com.playko.projectManagement.infrastructure.exception.UserNotInTeamException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.TeamEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.ITeamEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ITeamRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class TeamJpaAdapter implements ITeamPersistencePort {
    private final ITeamRepository teamRepository;
    private final ITeamEntityMapper teamEntityMapper;
    private final IUserRepository userRepository;
    private final IEmailHandler emailHandler;
    @Override
    public void saveTeam(TeamModel teamModel) {
        TeamEntity teamEntity = teamEntityMapper.toEntity(teamModel);
        teamRepository.save(teamEntity);
    }

    @Override
    public void addUserToTeam(Long teamId, String emailUser) {
        TeamEntity teamEntity = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
        UserEntity userEntity = userRepository.findByEmail(emailUser).orElseThrow(UserNotFoundException::new);

        List<UserEntity> updatedUsers = new ArrayList<>(teamEntity.getUsers());
        updatedUsers.add(userEntity);
        teamEntity.setUsers(updatedUsers);
        teamRepository.save(teamEntity);

        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setDestinatario(userEntity.getEmail());
        emailRequestDto.setAsunto("Has sido agregado al equipo " + teamEntity.getName());
        emailRequestDto.setMensaje("Hola " + userEntity.getName() + ",\n\n" +
                "Has sido agregado al equipo '" + teamEntity.getName() + "'.\n" +
                "Descripción: " + teamEntity.getDescription() + "\n\n" +
                "¡Bienvenido!");
        emailHandler.sendEmail(emailRequestDto);
    }


    @Override
    public void removeUserFromTeam(Long teamId, String emailUser) {
        TeamEntity teamEntity = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);
        UserEntity userEntity = userRepository.findByEmail(emailUser)
                .orElseThrow(UserNotFoundException::new);

        if (!teamEntity.getUsers().contains(userEntity)) {
            throw new UserNotInTeamException();
        }

        teamEntity.getUsers().remove(userEntity);
        teamRepository.save(teamEntity);
    }
}
