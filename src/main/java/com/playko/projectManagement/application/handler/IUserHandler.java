package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.UserRequestDto;

public interface IUserHandler {
    void saveUser(UserRequestDto userRequestDto);
}
