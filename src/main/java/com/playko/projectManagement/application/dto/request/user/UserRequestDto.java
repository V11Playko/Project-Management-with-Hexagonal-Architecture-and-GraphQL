package com.playko.projectManagement.application.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRequestDto {
    @NotNull(message = "El nombre es obligatorio.")
    private String name;

    @NotNull(message = "El apellido es obligatorio.")
    private String surname;

    @NotNull(message = "El DNI es obligatorio.")
    @Size(max = 10, message = "Maximo 10 caracteres.")
    @Pattern(regexp = "^[\\d]{1,3}\\.?[\\d]{3,3}\\.?[\\d]{3,3}$")
    private String dniNumber;

    @NotNull(message = "El celular es obligatorio.")
    @Size(max = 14,min = 10,message = "El numero de caracteres es invalido.")
    @Pattern(regexp = "[+]?[0-9]{10,12}")
    private String phone;

    @NotNull(message = "El correo es obligatorio.")
    @Email(regexp = "^(.+)@(.+)$", message = "El correo es invalido.")
    private String email;

    @NotNull(message = "La contraseña es obligatoria.")
    private String password;

    private String nameRole;
    private String descriptionRole;
    private String team;
}
