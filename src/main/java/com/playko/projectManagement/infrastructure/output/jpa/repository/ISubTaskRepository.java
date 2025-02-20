package com.playko.projectManagement.infrastructure.output.jpa.repository;

import com.playko.projectManagement.infrastructure.output.jpa.entity.SubTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISubTaskRepository extends JpaRepository<SubTaskEntity, Long> {
    List<SubTaskEntity> findByTaskId(Long taskId);
}
