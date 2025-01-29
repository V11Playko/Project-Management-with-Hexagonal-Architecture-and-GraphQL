package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.response.RoleResponseDto;

import java.util.List;

public interface IRoleHandler {
    List<RoleResponseDto> getAllRoles();
    RoleResponseDto getRole(Long id);
}
