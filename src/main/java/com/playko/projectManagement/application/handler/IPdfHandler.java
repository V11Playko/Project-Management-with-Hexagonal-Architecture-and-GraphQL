package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.team.TeamPerformanceReportDto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface IPdfHandler {
    ByteArrayOutputStream generatePdf(Long projectId) throws IOException;

    byte[] generatePerformanceReportPdf(Long teamId) throws IOException;
}
