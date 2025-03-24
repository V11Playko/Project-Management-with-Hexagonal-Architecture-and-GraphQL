package com.playko.projectManagement.application.handler.impl;

import com.playko.projectManagement.application.dto.request.EmailRequestDto;
import com.playko.projectManagement.application.handler.IEmailHandler;
import com.playko.projectManagement.domain.api.IEmailServicePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailHandler implements IEmailHandler {
    private final IEmailServicePort emailServicePort;

    @Override
    public void sendEmail(EmailRequestDto email) {
        emailServicePort.sendEmail(email);
    }
}
