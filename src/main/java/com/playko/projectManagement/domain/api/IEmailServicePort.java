package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.application.dto.request.EmailRequestDto;

public interface IEmailServicePort {
    void sendEmail(EmailRequestDto emailRequestDto);
}
