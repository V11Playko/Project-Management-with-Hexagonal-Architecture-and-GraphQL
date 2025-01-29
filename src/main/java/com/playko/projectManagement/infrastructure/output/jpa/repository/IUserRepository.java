package com.playko.projectManagement.infrastructure.output.jpa.repository;

import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
     UserEntity findByEmail(String email);
}
