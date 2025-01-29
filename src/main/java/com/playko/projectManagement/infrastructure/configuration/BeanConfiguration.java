package com.playko.projectManagement.infrastructure.configuration;

import com.playko.projectManagement.domain.api.IRoleServicePort;
import com.playko.projectManagement.domain.api.IUserServicePort;
import com.playko.projectManagement.domain.spi.IAuthPasswordEncoderPort;
import com.playko.projectManagement.domain.spi.IRolePersistencePort;
import com.playko.projectManagement.domain.spi.IUserPersistencePort;
import com.playko.projectManagement.domain.usecase.RoleUseCase;
import com.playko.projectManagement.domain.usecase.UserUseCase;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IRoleRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    @Bean
    public IAuthPasswordEncoderPort authPasswordEncoderPort() {
        return new AuthBcryptAdapter(encoder());
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IUserPersistencePort userPersistencePort(IUserRepository userRepository, IUserEntityMapper userEntityMapper) {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort) {
        return new UserUseCase(userPersistencePort, rolePersistencePort, authPasswordEncoderPort());
    }

    @Bean
    public IRoleServicePort roleServicePort(IRolePersistencePort rolePersistencePort) {
        return new RoleUseCase(rolePersistencePort);
    }
    @Bean
    public IRolePersistencePort rolePersistencePort(IRoleRepository roleRepository, IRoleEntityMapper roleEntityMapper) {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

}
