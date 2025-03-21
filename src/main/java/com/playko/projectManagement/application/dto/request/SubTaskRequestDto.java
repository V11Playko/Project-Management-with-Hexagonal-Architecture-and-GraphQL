package com.playko.projectManagement.application.dto.request;

import com.playko.projectManagement.shared.enums.SubTaskState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class SubTaskRequestDto {
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private SubTaskState state;
    private LocalDate limitDate;
    private String task;
}
