package com.playko.projectManagement.application.dto.response;

import com.playko.projectManagement.shared.enums.TaskPriority;
import com.playko.projectManagement.shared.enums.TaskState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TaskResponseDto {

    private String title;
    private String description;
    private TaskState state;
    private TaskPriority priority;
    private LocalDate limitDate;
    private Long projectId;
    private Long boardColumnId;
    private Long assignedUserId;
}
