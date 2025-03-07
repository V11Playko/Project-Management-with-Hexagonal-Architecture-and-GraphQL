package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.application.dto.response.ProjectStatsDto;
import com.playko.projectManagement.domain.model.ProjectModel;
import com.playko.projectManagement.domain.spi.IProjectPersistencePort;
import com.playko.projectManagement.infrastructure.exception.InvalidProjectStateException;
import com.playko.projectManagement.infrastructure.exception.ProjectNotFoundException;
import com.playko.projectManagement.infrastructure.exception.UserNotFoundException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.ProjectEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.RoleEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IProjectEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IProjectRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IRoleRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import com.playko.projectManagement.shared.constants.RolesId;
import com.playko.projectManagement.shared.enums.ProjectState;
import com.playko.projectManagement.shared.enums.RoleEnum;
import com.playko.projectManagement.shared.enums.TaskState;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@RequiredArgsConstructor
public class ProjectJpaAdapter implements IProjectPersistencePort {
    private final IProjectRepository projectRepository;
    private final IProjectEntityMapper projectEntityMapper;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    @Override
    public void createProject(ProjectModel projectModel) {
        ProjectEntity projectEntity = projectEntityMapper.toEntity(projectModel);
        projectRepository.save(projectEntity);

        UserEntity userEntity = userRepository.findByEmail(projectModel.getOwner())
                .orElseThrow(UserNotFoundException::new);
        RoleEntity roleEntity = roleRepository.findByName(String.valueOf(RoleEnum.ROLE_MANAGER));

        if (!userEntity.getRoleEntity().getId().equals(RolesId.ADMIN_ROLE_ID)) {
            userEntity.setRoleEntity(roleEntity);
            userRepository.save(userEntity);
        }
    }

    @Override
    public void updateProjectDeadline(Long projectId, LocalDate deadline) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
        project.setFinishedDate(deadline);
        projectRepository.save(project);
    }

    @Override
    public void archiveProject(Long projectId) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
        if (!project.getState().equals(ProjectState.COMPLETED)) {
            throw new InvalidProjectStateException();
        }

        project.setState(ProjectState.ARCHIVED);
        projectRepository.save(project);
    }

    @Override
    public ProjectStatsDto getProjectStats(Long projectId) {
        ProjectEntity projectEntity = projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);
        ProjectModel project = projectEntityMapper.toModel(projectEntity);

        long daysRemaining = project.getDaysRemaining();
        String progressStatus = project.getProgressStatus();
        Map<TaskState, Long> taskCounts = project.getTaskCountsByState();

        return new ProjectStatsDto(
                project.getId(),
                project.getName(),
                project.getCompletionPercentage(),
                daysRemaining,
                progressStatus,
                taskCounts.getOrDefault(TaskState.IN_PROGRESS, 0L).intValue()
                        + taskCounts.getOrDefault(TaskState.IN_PROGRESS, 0L).intValue()
                        + taskCounts.getOrDefault(TaskState.DONE, 0L).intValue(),
                taskCounts.getOrDefault(TaskState.DONE, 0L).intValue(),
                taskCounts.getOrDefault(TaskState.IN_PROGRESS, 0L).intValue(),
                taskCounts.getOrDefault(TaskState.PENDING, 0L).intValue()
        );
    }
}
