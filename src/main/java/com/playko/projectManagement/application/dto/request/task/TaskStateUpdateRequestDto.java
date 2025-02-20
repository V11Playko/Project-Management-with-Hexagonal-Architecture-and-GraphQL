package com.playko.projectManagement.application.dto.request.task;

import com.playko.projectManagement.shared.enums.TaskState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TaskStateUpdateRequestDto {
    @Enumerated(EnumType.STRING)
    private TaskState newState;
}
