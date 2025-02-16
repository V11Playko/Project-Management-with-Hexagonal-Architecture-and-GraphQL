package com.playko.projectManagement.application.dto.request.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TaskReassignmentRequestDto {
    private Long taskId;
    private Long newUserId;
}