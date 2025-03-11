package com.playko.projectManagement.application.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface IPdfHandler {
    ByteArrayOutputStream generatePdf(Long projectId) throws IOException;
}
