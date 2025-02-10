package com.playko.projectManagement.domain.usecase;

import com.playko.projectManagement.domain.api.IUserServicePort;
import com.playko.projectManagement.domain.model.RoleModel;
import com.playko.projectManagement.domain.model.UserModel;
import com.playko.projectManagement.domain.spi.IAuthPasswordEncoderPort;
import com.playko.projectManagement.domain.spi.IRolePersistencePort;
import com.playko.projectManagement.domain.spi.IUserPersistencePort;
import com.playko.projectManagement.shared.constants.RolesId;

import java.util.List;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IAuthPasswordEncoderPort authPasswordEncoderPort;

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort, IAuthPasswordEncoderPort authPasswordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
        this.authPasswordEncoderPort = authPasswordEncoderPort;
    }

    @Override
    public UserModel findByEmail(String email) {
        return userPersistencePort.findByEmail(email);
    }

    @Override
    public List<UserModel> findAllUsers() {
        return userPersistencePort.findAllUsers();
    }

    @Override
    public void saveUser(UserModel userModel) {
        userModel.setPassword(authPasswordEncoderPort.encodePassword(userModel.getPassword()));

        userPersistencePort.saveUser(userModel);
    }

    @Override
    public void updateUser(Long id, UserModel userModel) {
        userPersistencePort.updateUser(id, userModel);
    }

    @Override
    public void deleteUser(Long id) {
        userPersistencePort.deleteUser(id);
    }
}
