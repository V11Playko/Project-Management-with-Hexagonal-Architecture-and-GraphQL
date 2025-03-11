package com.playko.projectManagement.domain.spi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface IPdfPersistencePort {
    ByteArrayOutputStream generatePdf(Long projectId) throws IOException;
}
