package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.domain.model.UserModel;
import com.playko.projectManagement.domain.spi.IUserPersistencePort;
import com.playko.projectManagement.infrastructure.configuration.security.userDetails.CustomUserDetails;
import com.playko.projectManagement.infrastructure.exception.NoUsersFoundException;
import com.playko.projectManagement.infrastructure.exception.UnauthorizedException;
import com.playko.projectManagement.infrastructure.exception.UserAlreadyExistsException;
import com.playko.projectManagement.infrastructure.exception.UserNotFoundException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

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
        if (userRepository.findByEmail(userModel.getEmail()).isEmpty()) {
            throw new UserAlreadyExistsException();
        }
        UserEntity userEntity = userEntityMapper.toEntity(userModel);
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
        String correoAutenticado = obtenerCorreoDelToken();

        UserEntity authenticatedUser = userRepository.findByEmail(correoAutenticado).orElseThrow(UserNotFoundException::new);

        userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException());

        if (authenticatedUser.getRoleEntity().getName().equals("ROLE_ADMIN") || authenticatedUser.getId().equals(id)) {
            userRepository.deleteById(id);
            return;
        }

        throw new UnauthorizedException();
    }

    public String obtenerCorreoDelToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        throw new RuntimeException("Error obteniendo el correo del token.");
    }
}
