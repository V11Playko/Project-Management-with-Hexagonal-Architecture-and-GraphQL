package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.domain.model.RoleModel;

import java.util.List;

public interface IRolePersistencePort {
    List<RoleModel> getAllRoles();
    RoleModel getRole(Long id);
}
