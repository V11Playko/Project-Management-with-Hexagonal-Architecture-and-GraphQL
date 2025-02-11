package com.playko.projectManagement.infrastructure.output.jpa.repository;

import com.playko.projectManagement.infrastructure.output.jpa.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITaskRepository extends JpaRepository<TaskEntity, Long> {
}
