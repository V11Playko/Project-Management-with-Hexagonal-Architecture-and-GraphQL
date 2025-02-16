package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.user.UserRequestDto;
import com.playko.projectManagement.application.dto.request.user.UserUpdateRequestDto;
import com.playko.projectManagement.application.dto.response.UserResponseDto;

import java.util.List;

public interface IUserHandler {
    UserResponseDto findByEmail(String email);
    List<UserResponseDto> findAllUsers();
    void saveUser(UserRequestDto userRequestDto);
    void updateUser(Long id, UserUpdateRequestDto userUpdateRequestDto);
    void deleteUser(Long id);
}
