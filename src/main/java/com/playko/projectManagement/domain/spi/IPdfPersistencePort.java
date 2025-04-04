package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.application.dto.request.team.TeamPerformanceReportDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface IPdfPersistencePort {
    ByteArrayOutputStream generatePdf(Long projectId) throws IOException;

    byte[] generatePerformanceReportPdf(Long teamId) throws IOException;
}
