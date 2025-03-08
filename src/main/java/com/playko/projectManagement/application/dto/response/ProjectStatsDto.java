package com.playko.projectManagement.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectStatsDto {
    private Long projectId;
    private String projectName;
    private double completionPercentage;
    private long daysRemaining;
    private String progressStatus;
    private int totalTasks;
    private int completedTasks;
    private int inProgressTasks;
    private int pendingTasks;
}
