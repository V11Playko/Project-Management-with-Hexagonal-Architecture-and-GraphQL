package com.playko.projectManagement.infrastructure.configuration;

import com.playko.projectManagement.domain.spi.IAuthPasswordEncoderPort;
import com.playko.projectManagement.infrastructure.output.jpa.entity.RoleEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IRoleRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {
    private final IUserRepository userRepository;
    private final IAuthPasswordEncoderPort passwordEncoder;
    private final IRoleRepository roleRepository;

    public DatabaseInitializer(IUserRepository userRepository, IAuthPasswordEncoderPort passwordEncoder, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initialize() {
        initializeRoles();
        initializeAdminUser();
    }

    private void initializeRoles() {
        createRoleIfNotExists("ROLE_ADMIN", "ROLE_ADMIN");
        createRoleIfNotExists("ROLE_CONTRIBUIDOR", "ROLE_CONTRIBUIDOR");
        createRoleIfNotExists("ROLE_MANAGER", "ROLE_MANAGER");
        createRoleIfNotExists("ROLE_OBSERVADOR", "ROLE_OBSERVADOR");
        createRoleIfNotExists("ROLE_USUARIO", "ROLE_USUARIO");
    }

    private void createRoleIfNotExists(String name, String description) {
        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity();
            role.setName(name);
            role.setDescription(description);
            roleRepository.save(role);
        }
    }

    private void initializeAdminUser() {
        if (userRepository.findByEmail("edu.ufps10@gmail.com") == null) {
            UserEntity admin = new UserEntity();
            admin.setName("Admin");
            admin.setSurname("AdminSurname");
            admin.setPhone("+57 3136824595");
            admin.setDniNumber("10093875126");
            admin.setEmail("edu.ufps10@gmail.com");
            admin.setPassword(passwordEncoder.encodePassword("admin"));

            RoleEntity adminRole = roleRepository.findByName("ROLE_ADMIN");
            admin.setRoleEntity(adminRole);

            userRepository.save(admin);
        }
    }
}