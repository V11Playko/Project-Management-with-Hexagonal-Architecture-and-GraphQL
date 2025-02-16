package com.playko.projectManagement.domain.model;

import java.util.List;

public class BoardColumnModel {
    private Long id;
    private String name;
    private BoardModel board;
    private List<TaskModel> tasks;

    public BoardColumnModel(Long id, String name, BoardModel board, List<TaskModel> tasks) {
        this.id = id;
        this.name = name;
        this.board = board;
        this.tasks = tasks;
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

    public BoardModel getBoard() {
        return board;
    }

    public void setBoard(BoardModel board) {
        this.board = board;
    }

    public List<TaskModel> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskModel> tasks) {
        this.tasks = tasks;
    }
}
