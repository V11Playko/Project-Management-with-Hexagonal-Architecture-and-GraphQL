package com.playko.projectManagement.application.dto.response;

import com.playko.projectManagement.shared.enums.SubTaskState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SubTaskResponseDto {
    private String title;
    private String description;
    private SubTaskState state;
    private LocalDate limitDate;
    private String task;
}
