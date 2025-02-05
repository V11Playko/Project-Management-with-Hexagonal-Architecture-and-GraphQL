package com.playko.projectManagement.infrastructure.output.jpa.repository;

import com.playko.projectManagement.infrastructure.output.jpa.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeamRepository extends JpaRepository<TeamEntity, Long> {
}
