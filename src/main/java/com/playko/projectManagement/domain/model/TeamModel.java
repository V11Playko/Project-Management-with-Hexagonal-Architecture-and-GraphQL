package com.playko.projectManagement.domain.model;

import com.playko.projectManagement.shared.enums.TeamState;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.List;

public class TeamModel {
    private Long id;
    private String name;
    private String description;
    private LocalDate CreationDate;
    @Enumerated(EnumType.STRING)
    private TeamState state;
    private List<UserModel> users;

    public TeamModel(Long id, String name, String description, LocalDate creationDate, TeamState state, List<UserModel> users) {
        this.id = id;
        this.name = name;
        this.description = description;
        CreationDate = creationDate;
        this.state = state;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        CreationDate = creationDate;
    }

    public TeamState getState() {
        return state;
    }

    public void setState(TeamState state) {
        this.state = state;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
}
