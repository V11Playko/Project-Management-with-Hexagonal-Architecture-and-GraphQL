package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.domain.api.IPdfServicePort;
import com.playko.projectManagement.domain.spi.IPdfPersistencePort;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfUseCase implements IPdfServicePort {
    private final IPdfPersistencePort pdfPersistencePort;

    public PdfUseCase(IPdfPersistencePort pdfPersistencePort) {
        this.pdfPersistencePort = pdfPersistencePort;
    }

    @Override
    public ByteArrayOutputStream generatePdf(Long projectId) throws IOException {
        return pdfPersistencePort.generatePdf(projectId);
    }
}
