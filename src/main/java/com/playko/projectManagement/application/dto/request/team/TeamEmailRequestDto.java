package com.playko.projectManagement.application.dto.request.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TeamEmailRequestDto {
    private Long teamId;
    private String subject;
    private String message;
    private String remitente;
}
