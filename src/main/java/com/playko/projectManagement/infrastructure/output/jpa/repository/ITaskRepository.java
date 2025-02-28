package com.playko.projectManagement.infrastructure.output.jpa.repository;

import com.playko.projectManagement.infrastructure.output.jpa.entity.TaskEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ITaskRepository extends JpaRepository<TaskEntity, Long> {
    List<TaskEntity> findByAssignedUser(UserEntity user);
    @Query("SELECT t FROM TaskEntity t WHERE t.limitDate BETWEEN :now AND :tomorrow AND t.state != 'COMPLETED'")
    List<TaskEntity> findTasksDueSoon(@Param("now") LocalDateTime now, @Param("tomorrow") LocalDateTime tomorrow);
}
