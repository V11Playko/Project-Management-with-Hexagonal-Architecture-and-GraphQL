package com.playko.projectManagement.application.dto.request.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class TeamPerformanceReportDto {
    private Long teamId;
    private String teamName;
    private Map<String, Integer> completedTasksPerUser;
    private Map<String, Double> averageCompletionTime;
    private int totalTasks;
    private int completedTasks;
    private int inProgressTasks;
}
