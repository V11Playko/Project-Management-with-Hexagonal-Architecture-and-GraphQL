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
import com.playko.projectManagement.shared.enums.TaskPriority;
import com.playko.projectManagement.shared.enums.TaskState;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.Task;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
        );
        emailRequestDto.setMensaje(message);
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

        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setDestinatario(task.getAssignedUser().getEmail());
        emailRequestDto.setAsunto("Nueva Tarea Asignada");
        String message = String.format("Se te ha reasignado la tarea '%s'.", task.getTitle());
        emailRequestDto.setMensaje(message);
        emailHandler.sendEmail(emailRequestDto);
    }

    @Override
    public void updateTaskDeadline(Long taskId, LocalDate deadline) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        taskEntity.setLimitDate(deadline);
        taskRepository.save(taskEntity);

        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setDestinatario(taskEntity.getAssignedUser().getEmail());
        emailRequestDto.setAsunto("Actualización de Tarea");
        String message = String.format("Se te ha reasignado la tarea '%s'.", taskEntity.getTitle());
        emailRequestDto.setMensaje(message);
        emailHandler.sendEmail(emailRequestDto);
    }

    @Override
    public void updateTaskState(Long taskId, TaskState newState) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        taskEntity.setState(newState);
        taskRepository.save(taskEntity);

        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setDestinatario(taskEntity.getAssignedUser().getEmail());
        emailRequestDto.setAsunto("Cambio en Tarea");
        String message = String.format("Se te ha reasignado la tarea '%s'.", taskEntity.getTitle());
        emailRequestDto.setMensaje(message);
        emailHandler.sendEmail(emailRequestDto);
    }

    @Override
    public List<TaskModel> getTasksByFilters(Long boardId, TaskState state, TaskPriority priority) {
        List<TaskEntity> taskEntities;

        if (state != null && priority != null) {
            taskEntities = taskRepository.findByBoardColumn_Board_IdAndStateAndPriority(boardId, state, priority);
        } else if (state != null) {
            taskEntities = taskRepository.findByBoardColumn_Board_IdAndState(boardId, state);
        } else if (priority != null) {
            taskEntities = taskRepository.findByBoardColumn_Board_IdAndPriority(boardId, priority);
        } else {
            taskEntities = taskRepository.findByBoardColumn_Board_IdAndState(boardId, TaskState.PENDING); // Default filter
        }

        return taskEntities.stream()
                .map(taskEntityMapper::toModel)
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 8 * * ?", zone = "America/New_York")
    public void sendTaskReminders() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/New_York"));
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfTomorrow = now.toLocalDate().plusDays(1).atTime(23, 59, 59);

        List<TaskEntity> tasks = taskRepository.findTasksDueSoon(startOfToday, endOfTomorrow);
        tasks.stream()
                .filter(task -> task.getAssignedUser() != null)
                .forEach(task -> {
                    EmailRequestDto emailRequestDto = new EmailRequestDto();
                    emailRequestDto.setDestinatario(task.getAssignedUser().getEmail());
                    emailRequestDto.setAsunto("Recordatorio: Tarea próxima a vencer");
                    emailRequestDto.setMensaje("La tarea '" + task.getTitle() + "' vence el " + task.getLimitDate() + ".");
                    emailHandler.sendEmail(emailRequestDto);
                });
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
