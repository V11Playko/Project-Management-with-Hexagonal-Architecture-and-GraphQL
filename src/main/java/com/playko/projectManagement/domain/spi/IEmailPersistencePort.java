package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.application.dto.request.EmailRequestDto;

public interface IEmailPersistencePort {
    void sendEmail(EmailRequestDto emailRequestDto);
}
