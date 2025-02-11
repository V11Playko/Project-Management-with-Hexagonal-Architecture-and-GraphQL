package com.playko.projectManagement.application.dto.request;

import com.playko.projectManagement.shared.enums.TaskPriority;
import com.playko.projectManagement.shared.enums.TaskState;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TaskRequestDto {

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 100, message = "El título no puede tener más de 100 caracteres")
    private String title;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    private String description;

    @NotNull(message = "El estado de la tarea es obligatorio")
    private TaskState state;

    @NotNull(message = "La prioridad de la tarea es obligatoria")
    private TaskPriority priority;

    @FutureOrPresent(message = "La fecha límite debe ser hoy o en el futuro")
    private LocalDate limitDate;

    @NotNull(message = "El ID del proyecto es obligatorio")
    private Long projectId;

    @NotNull(message = "El ID de la columna del tablero es obligatorio")
    private Long boardColumnId;

    private Long assignedUserId;
}
