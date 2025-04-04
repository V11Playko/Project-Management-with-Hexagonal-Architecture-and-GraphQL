package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.application.dto.request.team.TeamPerformanceReportDto;
import com.playko.projectManagement.application.dto.response.ProjectStatsDto;
import com.playko.projectManagement.application.handler.IProjectHandler;
import com.playko.projectManagement.application.handler.ITeamHandler;
import com.playko.projectManagement.domain.spi.IPdfPersistencePort;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;


@RequiredArgsConstructor
public class PdfJpaAdapter implements IPdfPersistencePort  {
    private final IProjectHandler projectHandler;
    private final ITeamHandler teamHandler;

    @Override
    public ByteArrayOutputStream generatePdf(Long projectId) throws IOException {
        ProjectStatsDto stats = projectHandler.getProjectStats(projectId);

        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);

                // Centrar el título
                String title = stats.getProjectName();
                float titleWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(title) / 1000 * 16;
                float centerX = (page.getMediaBox().getWidth() - titleWidth) / 2;

                contentStream.beginText();
                contentStream.newLineAtOffset(centerX, 700);
                contentStream.showText(title);
                contentStream.endText();

                // Agregar estadísticas
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 650);
                contentStream.showText("Porcentaje Completado: " + stats.getCompletionPercentage() + "%");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Días Restantes: " + stats.getDaysRemaining());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Estado del Proyecto: " + stats.getProgressStatus());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Tareas Completadas: " + stats.getCompletedTasks() + "/" + stats.getTotalTasks());
                contentStream.endText();
            }

            document.save(outputStream);
            return outputStream;
        }
    }

    @Override
    public byte[] generatePerformanceReportPdf(Long teamId) throws IOException {
        TeamPerformanceReportDto report = teamHandler.generatePerformanceReport(teamId);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.beginText();
                contentStream.setLeading(20f);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Team Performance Report");
                contentStream.newLine();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.showText("Team: " + report.getTeamName());
                contentStream.newLine();
                contentStream.showText("Total Tasks: " + report.getTotalTasks());
                contentStream.newLine();
                contentStream.showText("Completed Tasks: " + report.getCompletedTasks());
                contentStream.newLine();
                contentStream.showText("In Progress Tasks: " + report.getInProgressTasks());
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("Performance per User:");
                contentStream.newLine();

                for (Map.Entry<String, Integer> entry : report.getCompletedTasksPerUser().entrySet()) {
                    contentStream.showText("- " + entry.getKey() + ": " + entry.getValue() + " tasks completed");
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }
}
