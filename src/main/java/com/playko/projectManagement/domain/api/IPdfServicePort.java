package com.playko.projectManagement.domain.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface IPdfServicePort {
    ByteArrayOutputStream generatePdf(Long projectId) throws IOException;
}
