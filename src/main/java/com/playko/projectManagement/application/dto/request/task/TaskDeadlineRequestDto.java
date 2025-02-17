package com.playko.projectManagement.application.dto.request.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TaskDeadlineRequestDto {
    private Long taskId;
    private LocalDate deadline;
}
