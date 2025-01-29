package com.playko.projectManagement.domain.spi;

import com.playko.projectManagement.domain.model.UserModel;

public interface IUserPersistencePort {
    void saveUser(UserModel userModel);
}
