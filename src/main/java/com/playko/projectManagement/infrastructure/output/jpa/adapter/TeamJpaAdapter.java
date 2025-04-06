package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.application.dto.request.EmailRequestDto;
import com.playko.projectManagement.application.dto.request.team.TeamEmailRequestDto;
import com.playko.projectManagement.application.dto.request.team.TeamPerformanceReportDto;
import com.playko.projectManagement.application.handler.IEmailHandler;
import com.playko.projectManagement.domain.model.TeamModel;
import com.playko.projectManagement.domain.spi.ITeamPersistencePort;
import com.playko.projectManagement.infrastructure.exception.TeamNotFoundException;
import com.playko.projectManagement.infrastructure.exception.UserNotFoundException;
import com.playko.projectManagement.infrastructure.exception.UserNotInTeamException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.TaskEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.TeamEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.ITeamEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ITaskRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ITeamRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import com.playko.projectManagement.shared.SecurityUtils;
import com.playko.projectManagement.shared.enums.TaskState;
import lombok.RequiredArgsConstructor;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TeamJpaAdapter implements ITeamPersistencePort {
    private final ITeamRepository teamRepository;
    private final ITeamEntityMapper teamEntityMapper;
    private final IUserRepository userRepository;
    private final IEmailHandler emailHandler;
    private final ITaskRepository taskRepository;
    private final SecurityUtils securityUtils;

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
        userEntity.setTeam(teamEntity);
        userRepository.save(userEntity);

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

    @Override
    public TeamPerformanceReportDto generatePerformanceReport(Long teamId) {
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        List<UserEntity> teamMembers = team.getUsers();
        List<TaskEntity> teamTasks = taskRepository.findByAssignedUser_Team_Id(teamId);

        Map<String, Integer> completedTasksPerUser = new HashMap<>();
        Map<String, Double> averageCompletionTime = new HashMap<>();
        int totalTasks = teamTasks.size();
        int completedTasks = 0;
        int inProgressTasks = 0;

        for (UserEntity user : teamMembers) {
            List<TaskEntity> userTasks = teamTasks.stream()
                    .filter(task -> task.getAssignedUser() != null && task.getAssignedUser().equals(user))
                    .collect(Collectors.toList());

            int userCompletedTasks = (int) userTasks.stream()
                    .filter(task -> task.getState() == TaskState.DONE)
                    .count();

            double avgTime = userTasks.stream()
                    .filter(task -> task.getState() == TaskState.DONE)
                    .mapToLong(task -> ChronoUnit.DAYS.between(task.getCreationDate(), task.getLimitDate()))
                    .average()
                    .orElse(0.0);

            completedTasksPerUser.put(user.getName(), userCompletedTasks);
            averageCompletionTime.put(user.getName(), avgTime);
        }

        completedTasks = (int) teamTasks.stream().filter(t -> t.getState() == TaskState.DONE).count();
        inProgressTasks = totalTasks - completedTasks;

        return new TeamPerformanceReportDto(teamId, team.getName(), completedTasksPerUser,
                averageCompletionTime, totalTasks, completedTasks, inProgressTasks);
    }

    @Override
    public void sendEmailToTeam(TeamEmailRequestDto emailRequest) {
        TeamEntity team = teamRepository.findById(emailRequest.getTeamId())
                .orElseThrow(TeamNotFoundException::new);

        String correo = securityUtils.obtenerCorreoDelToken();

        boolean isMember = team.getUsers().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(correo));

        if (!isMember) {
            throw new UserNotInTeamException();
        }

        for (UserEntity user : team.getUsers()) {
            EmailRequestDto individualEmail = new EmailRequestDto();
            individualEmail.setDestinatario(user.getEmail());
            individualEmail.setAsunto(emailRequest.getSubject());
            individualEmail.setMensaje("Hola " + user.getName() + ",\n\n" + emailRequest.getMessage());
            individualEmail.setRemitente(correo);

            emailHandler.sendEmail(individualEmail);
        }
    }
}
