package com.playko.projectManagement.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TaskAssignmentRequestDto {
    private Long taskId;
    private Long userId;
}
