package com.playko.projectManagement.domain.model;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserModel {
    private Long id;
    private String name;
    private String surname;
    private String dniNumber;
    private String phone;
    private String email;
    private String password;
    private RoleModel roleModel;
    private TeamModel team;

    public String HashedPassword(String password) {
        String hashedPassword = BCrypt.hashpw(this.password, BCrypt.gensalt());
        return hashedPassword;
    }

    public UserModel(Long id, String name, String surname, String dniNumber, String phone, String email, String password, RoleModel roleModel, TeamModel team) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dniNumber = dniNumber;
        this.phone = phone;
        this.email = email;
        this.password = HashedPassword(password);
        this.roleModel = roleModel;
        this.team = team;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDniNumber() {
        return dniNumber;
    }

    public void setDniNumber(String dniNumber) {
        this.dniNumber = dniNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }

    public TeamModel getTeam() {
        return team;
    }

    public void setTeam(TeamModel team) {
        this.team = team;
    }
}
