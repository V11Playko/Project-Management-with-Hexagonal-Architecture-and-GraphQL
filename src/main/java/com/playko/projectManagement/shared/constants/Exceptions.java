package com.playko.projectManagement.shared.constants;

public class Exceptions {

    private Exceptions() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Mensajes de Swagger
     */

    public static final String SWAGGER_TITLE_MESSAGE = "User API";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
    public static final String RESPONSE_MESSAGE_KEY = "Mensaje";

    /**
     * Mensajes para los correos
     */
    public static final String AFFAIR_NEW_TASK_ASSIGNMENT = "Se te ha asignado una nueva tarea";


    /**
     * Mensajes de excepciones
     */
    public static final String UNAUTHORIZED_MESSAGE = "No tienes permiso para acceder a este recurso";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "Este correo ya esta en uso.";
    public static final String USER_NOT_FOUND_MESSAGE = "Usuario no encontrado.";
    public static final String USERS_NOT_FOUND_MESSAGE= "Usuarios no encontrados.";
    public static final String TEAM_NOT_FOUND_MESSAGE= "Team no encontrado.";
    public static final String USER_NOT_FOUND_IN_TEAM_MESSAGE= "Usuario no existe en el equipo.";
    public static final String PROJECT_ALREADY_EXISTS_MESSAGE = "Este proyecto ya esta en uso.";
    public static final String PROJECT_NOT_FOUND_MESSAGE = "Este projecto no existe.";
    public static final String BOARD_COLUMN_NOT_FOUND_MESSAGE  = "Esta columna de la tabla no existe.";
    public static final String TASK_NOT_FOUND_MESSAGE = "Esta tarea no existe.";
    public static final String INVALID_PROJECT_STATE_MESSAGE = "Sólo se pueden archivar los proyectos finalizados.";
    public static final String SUB_TASK_NOT_FOUND_MESSAGE= "Subtarea no encontrada.";
    public static final String EMAIL_NOT_SEND_MESSAGE= "El correo no se envio correctamente..";
    public static final String BOARD_NOT_FOUND_MESSAGE = "Esta tabla Kanban no existe.";
    public static final String INVALID_TASK_STATE_MESSAGE = "La tarea aún no ha sido completada.";
    public static final String USER_ALREADY_RESTRICTED_MESSAGE = "El usuario ya esta restringido.";
    public static final String USER_NOT_RESTRICTED_MESSAGE = "El usuario indicado no esta restringido.";

}
