package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.domain.spi.ITaskPersistencePort;
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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskJpaAdapter implements ITaskPersistencePort {
    private final ITaskRepository taskRepository;
    private final IProjectRepository projectRepository;
    private final IBoardColumnRepository boardColumnRepository;
    private final IUserRepository userRepository;
    private final ITaskEntityMapper taskEntityMapper;

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

        taskEntity.setAssignedUser(userEntity);
        taskRepository.save(taskEntity);
    }
}
