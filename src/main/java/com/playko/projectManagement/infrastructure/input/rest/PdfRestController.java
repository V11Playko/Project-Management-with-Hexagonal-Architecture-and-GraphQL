package com.playko.projectManagement.infrastructure.input.rest;

import com.playko.projectManagement.application.handler.IPdfHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PreAuthorize("hasAnyRole('MANAGER', 'OBSERVER', 'USER')")
    @GetMapping("/export/{projectId}")
    public ResponseEntity<InputStreamResource> exportProjectReport(@PathVariable Long projectId) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = pdfHandler.generatePdf(projectId);

        String fileName = "projectStats";

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename="+ fileName +".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
    }
}
