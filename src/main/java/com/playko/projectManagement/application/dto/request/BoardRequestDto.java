package com.playko.projectManagement.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BoardRequestDto {
    private String name;
    private Long projectId;
}
