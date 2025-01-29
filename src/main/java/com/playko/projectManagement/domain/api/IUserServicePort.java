package com.playko.projectManagement.domain.api;

import com.playko.projectManagement.domain.model.UserModel;

public interface IUserServicePort {
    void saveUser(UserModel userModel);
}
