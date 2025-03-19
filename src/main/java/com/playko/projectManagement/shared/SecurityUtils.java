package com.playko.projectManagement.shared;

import com.playko.projectManagement.infrastructure.configuration.security.userDetails.CustomUserDetails;
import com.playko.projectManagement.infrastructure.exception.ProjectNotFoundException;
import com.playko.projectManagement.infrastructure.exception.UnauthorizedException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.ProjectEntity;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IProjectRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    private final IProjectRepository projectRepository;

    public SecurityUtils(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void validarAccesoProyecto(Long projectId, String userEmail) {
        ProjectEntity project = projectRepository.findById(projectId)
                .orElseThrow(ProjectNotFoundException::new);

        if (project.getRestrictedUsers().contains(userEmail)) {
            throw new UnauthorizedException();
        }
    }

    public String obtenerCorreoDelToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new RuntimeException("Error obteniendo el correo del token.");
    }
}
