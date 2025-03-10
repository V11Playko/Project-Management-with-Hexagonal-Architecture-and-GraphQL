package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.application.dto.response.ProjectStatsDto;
import com.playko.projectManagement.application.handler.IProjectHandler;
import com.playko.projectManagement.domain.spi.IPdfPersistencePort;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
public class PdfJpaAdapter implements IPdfPersistencePort  {
    private final IProjectHandler projectHandler;

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
}
