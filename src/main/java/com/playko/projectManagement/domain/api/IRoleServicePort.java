package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.domain.model.RoleModel;

import java.util.List;

public interface IRoleServicePort {
    List<RoleModel> getAllRoles();
    RoleModel getRole(Long id);
}
