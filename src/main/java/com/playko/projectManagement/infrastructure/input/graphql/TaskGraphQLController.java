package com.playko.projectManagement.infrastructure.input.graphql;

import com.playko.projectManagement.application.dto.response.TaskResponseDto;
import com.playko.projectManagement.application.handler.ITaskHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskGraphQLController {
    private final ITaskHandler taskHandler;

    @QueryMapping
    public List<TaskResponseDto> searchTasksByKeyword(@Argument String keyword) {
        return taskHandler.searchTasksByKeyword(keyword);
    }
}
