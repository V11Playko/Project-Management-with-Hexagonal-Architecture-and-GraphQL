package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.response.RoleResponseDto;
import com.playko.projectManagement.application.handler.IRoleHandler;
import com.playko.projectManagement.application.mapper.response.IRoleResponseMapper;
import com.playko.projectManagement.domain.api.IRoleServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleHandler implements IRoleHandler {
    private final IRoleResponseMapper roleResponseMapper;
    private final IRoleServicePort roleServicePort;

    @Override
    public List<RoleResponseDto> getAllRoles() {
        return roleResponseMapper.toResponseList(roleServicePort.getAllRoles());
    }

    @Override
    public RoleResponseDto getRole(Long id) {
        return roleResponseMapper.toResponse(roleServicePort.getRole(id));
    }
}
