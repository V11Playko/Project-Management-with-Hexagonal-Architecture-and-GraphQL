package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.LoginRequestDto;
import com.playko.projectManagement.application.dto.response.JwtTokenResponseDto;
import com.playko.projectManagement.application.handler.IAuthHandler;
import com.playko.projectManagement.infrastructure.configuration.security.jwt.JwtUtils;
import com.playko.projectManagement.infrastructure.configuration.security.userDetails.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthHandler implements IAuthHandler {
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtTokenResponseDto loginUser(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtTokenResponseDto(jwt, userDetails.getUsername(), roles);
    }
}