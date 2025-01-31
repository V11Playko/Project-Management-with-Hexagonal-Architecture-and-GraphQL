package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.domain.model.UserModel;
import com.playko.projectManagement.domain.spi.IUserPersistencePort;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    @Override
    public UserModel findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return userEntityMapper.toUserModel(userEntity);
    }

    @Override
    public List<UserModel> findAllUsers() {
        List<UserEntity> entityList = userRepository.findAll();

        return userEntityMapper.toUserModelList(entityList);

    }

    @Override
    public void saveUser(UserModel userModel) {
        UserEntity userEntity = userEntityMapper.toEntity(userModel);
        userRepository.save(userEntity);
    }

    @Override
    public void updateUser(Long id, UserModel userModel) {
        userRepository.save(userEntityMapper.toEntity(userModel));
    }
}
