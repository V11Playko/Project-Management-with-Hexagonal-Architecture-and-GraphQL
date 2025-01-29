package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.UserRequestDto;
import com.playko.projectManagement.application.handler.IUserHandler;
import com.playko.projectManagement.application.mapper.request.IUserRequestMapper;
import com.playko.projectManagement.domain.api.IUserServicePort;
import com.playko.projectManagement.domain.model.UserModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserHandler implements IUserHandler {
    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;
    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        UserModel userModel = userRequestMapper.toUserRequest(userRequestDto);
        userServicePort.saveUser(userModel);
    }
}
