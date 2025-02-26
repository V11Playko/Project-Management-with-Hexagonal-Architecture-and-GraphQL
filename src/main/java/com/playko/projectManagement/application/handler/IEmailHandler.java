package com.playko.projectManagement.application.handler;

import com.playko.projectManagement.application.dto.request.EmailRequestDto;

public interface IEmailHandler {
    void sendEmail(EmailRequestDto email);
}
