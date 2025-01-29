package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.domain.api.IRoleServicePort;
import com.playko.projectManagement.domain.model.RoleModel;
import com.playko.projectManagement.domain.spi.IRolePersistencePort;

import java.util.List;

public class RoleUseCase implements IRoleServicePort {
    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public List<RoleModel> getAllRoles() {
        return rolePersistencePort.getAllRoles();
    }

    @Override
    public RoleModel getRole(Long id) {
        return rolePersistencePort.getRole(id);
    }
}
