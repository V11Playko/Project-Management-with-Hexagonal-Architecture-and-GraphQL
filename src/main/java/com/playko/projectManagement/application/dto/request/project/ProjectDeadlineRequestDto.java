package com.playko.projectManagement.application.dto.request.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ProjectDeadlineRequestDto {
    private Long projectId;
    private LocalDate deadline;
}
