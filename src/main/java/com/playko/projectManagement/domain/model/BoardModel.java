package com.playko.projectManagement.domain.model;

import java.util.List;

public class BoardModel {
    private Long id;
    private String name;
    private List<BoardColumnModel> columns;
    private ProjectModel project;

    public BoardModel(Long id, String name, List<BoardColumnModel> columns, ProjectModel project) {
        this.id = id;
        this.name = name;
        this.columns = columns;
        this.project = project;
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

    public List<BoardColumnModel> getColumns() {
        return columns;
    }

    public void setColumns(List<BoardColumnModel> columns) {
        this.columns = columns;
    }

    public ProjectModel getProject() {
        return project;
    }

    public void setProject(ProjectModel project) {
        this.project = project;
    }
}
