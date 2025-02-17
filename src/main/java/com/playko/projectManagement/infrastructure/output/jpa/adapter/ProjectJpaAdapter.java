package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.domain.model.ProjectModel;
import com.playko.projectManagement.domain.spi.IProjectPersistencePort;
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
import com.playko.projectManagement.shared.enums.RoleEnum;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

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
}
