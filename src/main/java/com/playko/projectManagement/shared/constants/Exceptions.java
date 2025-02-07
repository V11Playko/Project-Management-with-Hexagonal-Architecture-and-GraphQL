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
     * Mensajes de excepciones
     */
    public static final String UNAUTHORIZED_MESSAGE = "No tienes permiso para acceder a este recurso.";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "Este correo ya esta en uso.";
    public static final String USER_NOT_FOUND_MESSAGE = "Usuario no encontrado.";
    public static final String USERS_NOT_FOUND_MESSAGE= "Usuarios no encontrados.";
    public static final String TEAM_NOT_FOUND_MESSAGE= "Team no encontrado.";
    public static final String USER_NOT_FOUND_IN_TEAM_MESSAGE= "Usuario no existe en el equipo.";

}
