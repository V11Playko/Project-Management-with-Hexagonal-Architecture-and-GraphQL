package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.domain.model.RoleModel;
import com.playko.projectManagement.domain.spi.IRolePersistencePort;
import com.playko.projectManagement.infrastructure.output.jpa.entity.RoleEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public List<RoleModel> getAllRoles() {
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        return roleEntityMapper.toRoleList(roleEntityList);
    }

    @Override
    public RoleModel getRole(Long id) {
        RoleEntity role = roleRepository.findById(id).get();
        return roleEntityMapper.toRole(role);
    }
}
