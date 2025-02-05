package com.playko.projectManagement.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TeamRequestDto {
    @NotBlank(message = "El nombre del equipo es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    private List<String> userEmails;
}
