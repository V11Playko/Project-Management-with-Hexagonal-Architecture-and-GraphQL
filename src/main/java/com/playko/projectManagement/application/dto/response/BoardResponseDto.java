package com.playko.projectManagement.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BoardResponseDto {
    private String name;
    private List<String> columnsName;

    private String projectName;
}
