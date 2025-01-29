package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.domain.model.UserModel;
import com.playko.projectManagement.domain.spi.IUserPersistencePort;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    @Override
    public void saveUser(UserModel userModel) {
        UserEntity userEntity = userEntityMapper.toEntity(userModel);
        userRepository.save(userEntity);
    }
}
