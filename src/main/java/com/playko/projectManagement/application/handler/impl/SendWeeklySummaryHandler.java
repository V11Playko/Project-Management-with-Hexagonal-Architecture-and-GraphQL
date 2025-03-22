package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.EmailRequestDto;
import com.playko.projectManagement.application.dto.response.ProjectStatsDto;
import com.playko.projectManagement.application.handler.IProjectHandler;
import com.playko.projectManagement.infrastructure.exception.ProjectsNotFoundException;
import com.playko.projectManagement.infrastructure.exception.StatsNotFoundException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.ProjectEntity;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IProjectRepository;
import com.playko.projectManagement.shared.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class SendWeeklySummaryHandler {
    private final SecurityUtils securityUtils;
    private final IProjectRepository projectRepository;
    private final IProjectHandler projectHandler;
    private final EmailHandler emailHandler;

    @Scheduled(cron = "0 0 8 * * MON") // Todos los lunes 8AM
    public void sendWeeklySummary() {
        String correoAutenticado = securityUtils.obtenerCorreoDelToken();
        List<ProjectEntity> proyectos = projectRepository.findByOwner(correoAutenticado);
        if (proyectos.isEmpty()) {
            throw new ProjectsNotFoundException();
        }

        StringBuilder mensajeResumen = new StringBuilder("📌 **Resumen Semanal de tus Proyectos** 📌\n\n");

        for (ProjectEntity project : proyectos) {
            ProjectStatsDto stats = projectHandler.getProjectStats(project.getId());
            if (stats == null) {
                throw new StatsNotFoundException(project.getId());
            }

            mensajeResumen.append(String.format(
                    "🔹 **%s**\n" +
                            "   - ⏳ Días restantes: %d\n" +
                            "   - 📊 Progreso: %s\n" +
                            "   - 📌 Total de tareas: %d\n" +
                            "   - ✅ Completadas: %d\n" +
                            "   - 🚧 En progreso: %d\n" +
                            "   - 🕒 Pendientes: %d\n\n",
                    stats.getProjectName(), stats.getDaysRemaining(), stats.getProgressStatus(),
                    stats.getTotalTasks(), stats.getCompletedTasks(),
                    stats.getInProgressTasks(), stats.getPendingTasks()
            ));
        }

        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setDestinatario(correoAutenticado);
        emailRequestDto.setAsunto("📅 Resumen Semanal de tus Proyectos");
        emailRequestDto.setMensaje(mensajeResumen.toString());

        emailHandler.sendEmail(emailRequestDto);
    }

}
