package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.application.dto.request.EmailRequestDto;
import com.playko.projectManagement.domain.api.IEmailServicePort;
import com.playko.projectManagement.domain.spi.IEmailPersistencePort;

public class EmailUseCase implements IEmailServicePort {
    private final IEmailPersistencePort emailPersistencePort;

    public EmailUseCase(IEmailPersistencePort emailPersistencePort) {
        this.emailPersistencePort = emailPersistencePort;
    }

    @Override
    public void sendEmail(EmailRequestDto emailRequestDto) {
        emailPersistencePort.sendEmail(emailRequestDto);
    }
}
