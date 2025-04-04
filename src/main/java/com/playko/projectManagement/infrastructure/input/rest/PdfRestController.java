package com.playko.projectManagement.infrastructure.input.rest;

import com.playko.projectManagement.application.dto.request.team.TeamPerformanceReportDto;
import com.playko.projectManagement.application.handler.IPdfHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/pdf")
@RequiredArgsConstructor
public class PdfRestController {
    private final IPdfHandler pdfHandler;

    @Operation(summary = "Export project report as PDF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PDF report generated successfully",
                    content = @Content(mediaType = "application/pdf")),
            @ApiResponse(responseCode = "404", description = "Project not found"),
            @ApiResponse(responseCode = "500", description = "Error generating PDF report")
    })
    @PreAuthorize("hasAnyRole('MANAGER', 'OBSERVER', 'USER')")
    @GetMapping("/export/{projectId}")
    public ResponseEntity<InputStreamResource> exportProjectReport(@PathVariable Long projectId) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = pdfHandler.generatePdf(projectId);

        String fileName = "projectStats";

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + fileName + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
    }

    @Operation(summary = "Download team performance report as PDF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PDF generated successfully"),
            @ApiResponse(responseCode = "404", description = "Team not found")
    })
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/performance/{teamId}")
    public ResponseEntity<ByteArrayResource> downloadPerformanceReport(@PathVariable Long teamId) {
        try {
            byte[] pdfBytes = pdfHandler.generatePerformanceReportPdf(teamId);
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=team_performance.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfBytes.length)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
