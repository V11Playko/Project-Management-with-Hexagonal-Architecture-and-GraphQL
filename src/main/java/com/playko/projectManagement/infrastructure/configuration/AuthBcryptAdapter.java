package com.playko.projectManagement.infrastructure.configuration;

import com.playko.projectManagement.domain.spi.IAuthPasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AuthBcryptAdapter implements IAuthPasswordEncoderPort {
    private final PasswordEncoder encoder;

    /**
     * Returns an encoded password based on the encoder (BCrypt)
     *
     * @param decodedPassword - password in plain text
     * @return encoded password
     * */
    @Override
    public String encodePassword(String decodedPassword) {
        return this.encoder.encode(decodedPassword);
    }

    /**
     * Returns a decoded password
     * @param encodedPassword - encoded password
     */
    @Override
    public String decodePassword(String encodedPassword) {
        return null;
    }
}
