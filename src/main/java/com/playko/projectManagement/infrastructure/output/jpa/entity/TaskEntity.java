package com.playko.projectManagement.infrastructure.output.jpa.entity;

import com.playko.projectManagement.shared.enums.TaskPriority;
import com.playko.projectManagement.shared.enums.TaskState;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskState state;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private LocalDate limitDate;
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @ManyToOne
    @JoinColumn(name = "board_column_id", nullable = false)
    private BoardColumnEntity boardColumn;

    @OneToMany(mappedBy = "task")
    private List<SubTaskEntity> subtasks;

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private UserEntity assignedUser;

    @PrePersist
    protected void onCreate() {
        this.creationDate = LocalDate.now(); // Se inicializa con la fecha actual
    }
}
