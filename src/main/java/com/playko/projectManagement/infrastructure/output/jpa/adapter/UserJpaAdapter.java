package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.domain.model.UserModel;
import com.playko.projectManagement.domain.spi.IUserPersistencePort;
import com.playko.projectManagement.infrastructure.exception.NoUsersFoundException;
import com.playko.projectManagement.infrastructure.exception.UnauthorizedException;
import com.playko.projectManagement.infrastructure.exception.UserAlreadyExistsException;
import com.playko.projectManagement.infrastructure.exception.UserNotFoundException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.RoleEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IRoleRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import com.playko.projectManagement.shared.SecurityUtils;
import com.playko.projectManagement.shared.enums.RoleEnum;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleRepository roleRepository;
    private final SecurityUtils securityUtils;

    @Override
    public UserModel findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return userEntityMapper.toUserModel(userEntity);
    }

    @Override
    public List<UserModel> findAllUsers() {
        List<UserEntity> entityList = userRepository.findAll();
        if (entityList.isEmpty()) {
            throw new NoUsersFoundException();
        }
        return userEntityMapper.toUserModelList(entityList);
    }

    @Override
    public void saveUser(UserModel userModel) {
        RoleEntity role = roleRepository.findByName(String.valueOf(RoleEnum.ROLE_USER));
        if (userRepository.findByEmail(userModel.getEmail()).isEmpty()) {
            throw new UserAlreadyExistsException();
        }
        UserEntity userEntity = userEntityMapper.toEntity(userModel);
        userEntity.setRoleEntity(role);
        userRepository.save(userEntity);
    }

    @Override
    public void updateUser(Long id, UserModel userModel) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        UserEntity updatedUser = userEntityMapper.toEntity(userModel);
        updatedUser.setId(existingUser.getId()); // Asegurar que la entidad mantenga el ID
        userRepository.save(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        String correoAutenticado = securityUtils.obtenerCorreoDelToken();

        UserEntity authenticatedUser = userRepository.findByEmail(correoAutenticado).orElseThrow(UserNotFoundException::new);

        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        if (authenticatedUser.getRoleEntity().getName().equals("ROLE_ADMIN") || authenticatedUser.getId().equals(id)) {
            userRepository.deleteById(id);
            return;
        }

        throw new UnauthorizedException();
    }
}
