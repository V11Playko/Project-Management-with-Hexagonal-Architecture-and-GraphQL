package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.LoginRequestDto;
import com.playko.projectManagement.application.dto.response.JwtTokenResponseDto;

public interface IAuthHandler {
    JwtTokenResponseDto loginUser(LoginRequestDto loginRequestDto);

}