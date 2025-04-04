package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.application.dto.request.team.TeamPerformanceReportDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface IPdfServicePort {
    ByteArrayOutputStream generatePdf(Long projectId) throws IOException;

    byte[] generatePerformanceReportPdf(Long teamId) throws IOException;
}
