package com.playko.projectManagement.infrastructure.output.jpa.repository;

import com.playko.projectManagement.infrastructure.output.jpa.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
