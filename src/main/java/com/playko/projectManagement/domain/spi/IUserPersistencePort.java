package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.domain.model.UserModel;

import java.util.List;

public interface IUserPersistencePort {
    UserModel findByEmail(String email);
    List<UserModel> findAllUsers();
    void saveUser(UserModel userModel);
    void updateUser(Long id, UserModel userModel);
}
