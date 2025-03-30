package com.playko.projectManagement.application.dto.request.project;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRestrictionRequestDto {

    @NotNull(message = "El ID del proyecto no puede ser nulo")
    private Long projectId;

    @NotNull(message = "El correo electrónico no puede ser nulo")
    @Email(message = "Debe proporcionar un correo electrónico válido")
    private String email;
}
