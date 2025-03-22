package com.playko.projectManagement.infrastructure.exception;

public class StatsNotFoundException extends RuntimeException {
    public StatsNotFoundException(Long projectId) {
        super("No se encontraron estadísticas para el proyecto con ID: " + projectId);
    }
}
