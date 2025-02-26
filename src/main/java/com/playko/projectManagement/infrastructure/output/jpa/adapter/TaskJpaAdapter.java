package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.application.dto.request.EmailRequestDto;
import com.playko.projectManagement.application.handler.IEmailHandler;
import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.domain.spi.ITaskPersistencePort;
import com.playko.projectManagement.infrastructure.configuration.security.userDetails.CustomUserDetails;
import com.playko.projectManagement.infrastructure.exception.BoardColumnNotFoundException;
import com.playko.projectManagement.infrastructure.exception.ProjectNotFoundException;
import com.playko.projectManagement.infrastructure.exception.TaskNotFoundException;
import com.playko.projectManagement.infrastructure.exception.UserNotFoundException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.BoardColumnEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.ProjectEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.TaskEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.ITaskEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IBoardColumnRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IProjectRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ITaskRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import com.playko.projectManagement.shared.constants.Exceptions;
import com.playko.projectManagement.shared.enums.TaskState;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TaskJpaAdapter implements ITaskPersistencePort {
    private final ITaskRepository taskRepository;
    private final IProjectRepository projectRepository;
    private final IBoardColumnRepository boardColumnRepository;
    private final IUserRepository userRepository;
    private final ITaskEntityMapper taskEntityMapper;
    private final IEmailHandler emailHandler;

    @Override
    public List<TaskModel> getTasksByUserEmail() {
          String correoDelToken = obtenerCorreoDelToken();
          UserEntity userEntity = userRepository.findByEmail(correoDelToken)
                  .orElseThrow(UserNotFoundException::new);

          List<TaskEntity> taskEntities = taskRepository.findByAssignedUser(userEntity);
          return taskEntityMapper.toDtoList(taskEntities);
    }

    @Override
    public void saveTask(TaskModel taskModel) {
        ProjectEntity project = projectRepository.findById(taskModel.getProject().getId())
                .orElseThrow(ProjectNotFoundException::new);
        BoardColumnEntity boardColumn = boardColumnRepository.findById(taskModel.getBoardColumn().getId())
                .orElseThrow(BoardColumnNotFoundException::new);
        UserEntity user = userRepository.findById(taskModel.getAssignedUserId().getId())
                .orElseThrow(UserNotFoundException::new);

        TaskEntity taskEntity = taskEntityMapper.toEntity(taskModel);
        taskEntity.setProject(project);
        taskEntity.setBoardColumn(boardColumn);
        taskEntity.setAssignedUser(user);

        taskRepository.save(taskEntity);
    }

    @Override
    public void assignTaskToUser(Long taskId, Long userId) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setDestinatario(userEntity.getEmail());
        emailRequestDto.setAsunto(Exceptions.AFFAIR_NEW_TASK_ASSIGNMENT);
        String message = String.format(
                "Hola %s,\n\nSe te ha asignado la tarea número %d. Para más información, comunícate con el manager de equipo.\n\nSaludos.",
                userEntity.getName(), taskId
        );        emailRequestDto.setMensaje(message);
        emailHandler.sendEmail(emailRequestDto);

        taskEntity.setAssignedUser(userEntity);
        taskRepository.save(taskEntity);
    }

    @Override
    public void reassignTask(Long taskId, Long newUserId) {
        TaskEntity task = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        UserEntity newUser = userRepository.findById(newUserId)
                .orElseThrow(UserNotFoundException::new);

        task.setAssignedUser(newUser);
        taskRepository.save(task);
    }

    @Override
    public void updateTaskDeadline(Long taskId, LocalDate deadline) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        taskEntity.setLimitDate(deadline);
        taskRepository.save(taskEntity);
    }

    @Override
    public void updateTaskState(Long taskId, TaskState newState) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        taskEntity.setState(newState);
        taskRepository.save(taskEntity);
    }

    public String obtenerCorreoDelToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new RuntimeException("Error obteniendo el correo del token.");
    }
}
