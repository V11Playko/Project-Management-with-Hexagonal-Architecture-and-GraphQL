package com.playko.projectManagement.application.dto.request.task;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveTaskRequestDto {

    @NotNull(message = "El ID de la tarea no puede ser nulo")
    private Long taskId;

    @NotNull(message = "El ID de la columna destino no puede ser nulo")
    private Long targetColumnId;
}
