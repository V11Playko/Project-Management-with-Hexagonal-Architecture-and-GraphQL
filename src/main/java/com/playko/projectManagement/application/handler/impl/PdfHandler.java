package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.team.TeamPerformanceReportDto;
import com.playko.projectManagement.application.handler.IPdfHandler;
import com.playko.projectManagement.domain.api.IPdfServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class PdfHandler implements IPdfHandler {
    private final IPdfServicePort pdfServicePort;
    @Override
    public ByteArrayOutputStream generatePdf(Long projectId) throws IOException {
        return pdfServicePort.generatePdf(projectId);
    }

    @Override
    public byte[] generatePerformanceReportPdf(Long teamId) throws IOException {
        return pdfServicePort.generatePerformanceReportPdf(teamId);
    }
}
