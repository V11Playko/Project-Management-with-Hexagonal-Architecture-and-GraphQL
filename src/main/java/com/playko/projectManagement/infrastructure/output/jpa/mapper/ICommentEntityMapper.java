package com.playko.projectManagement.infrastructure.output.jpa.mapper;

import com.playko.projectManagement.domain.model.CommentModel;
import com.playko.projectManagement.domain.model.RoleModel;
import com.playko.projectManagement.domain.model.TaskModel;
import com.playko.projectManagement.domain.model.UserModel;
import com.playko.projectManagement.infrastructure.output.jpa.entity.CommentEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.RoleEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.TaskEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = { IUserEntityMapper.class, ITaskEntityMapper.class })
public interface ICommentEntityMapper {

    ICommentEntityMapper INSTANCE = Mappers.getMapper(ICommentEntityMapper.class);

    @Mapping(target = "user", source = "user", qualifiedByName = "mapUserEntityToModel")
    @Mapping(target = "task", source = "task", qualifiedByName = "mapTaskEntityToModel")
    CommentModel toModel(CommentEntity commentEntity);

    @Mapping(target = "user", source = "user", qualifiedByName = "mapUserModelToEntity")
    @Mapping(target = "task", source = "task", qualifiedByName = "mapTaskModelToEntity")
    CommentEntity toEntity(CommentModel commentModel);

    List<CommentModel> toModelList(List<CommentEntity> commentEntities);

    // 🔥 Métodos auxiliares para mapear UserEntity <-> UserModel
    @Named("mapUserEntityToModel")
    default UserModel mapUserEntityToModel(UserEntity userEntity) {
        if (userEntity == null) return null;
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setName(userEntity.getName());
        userModel.setSurname(userEntity.getSurname());
        userModel.setDniNumber(userEntity.getDniNumber());
        userModel.setPhone(userEntity.getPhone());
        userModel.setEmail(userEntity.getEmail());
        userModel.setPassword(userEntity.getPassword());
        userModel.setRoleModel(mapRoleEntityToModel(userEntity.getRoleEntity())); // Mapeo manual de Role
        return userModel;
    }

    @Named("mapUserModelToEntity")
    default UserEntity mapUserModelToEntity(UserModel userModel) {
        if (userModel == null) return null;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userModel.getId());
        userEntity.setName(userModel.getName());
        userEntity.setSurname(userModel.getSurname());
        userEntity.setDniNumber(userModel.getDniNumber());
        userEntity.setPhone(userModel.getPhone());
        userEntity.setEmail(userModel.getEmail());
        userEntity.setPassword(userModel.getPassword());
        userEntity.setRoleEntity(mapRoleModelToEntity(userModel.getRoleModel())); // Mapeo manual de Role
        return userEntity;
    }

    // 🔥 Métodos auxiliares para mapear TaskEntity <-> TaskModel
    @Named("mapTaskEntityToModel")
    default TaskModel mapTaskEntityToModel(TaskEntity taskEntity) {
        if (taskEntity == null) return null;
        TaskModel taskModel = new TaskModel();
        taskModel.setId(taskEntity.getId());
        taskModel.setTitle(taskEntity.getTitle());
        taskModel.setDescription(taskEntity.getDescription());
        taskModel.setState(taskEntity.getState());
        taskModel.setPriority(taskEntity.getPriority());
        taskModel.setLimitDate(taskEntity.getLimitDate());
        taskModel.setAssignedUserId(mapUserEntityToModel(taskEntity.getAssignedUser())); // Mapeo de usuario asignado
        return taskModel;
    }

    @Named("mapTaskModelToEntity")
    default TaskEntity mapTaskModelToEntity(TaskModel taskModel) {
        if (taskModel == null) return null;
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(taskModel.getId());
        taskEntity.setTitle(taskModel.getTitle());
        taskEntity.setDescription(taskModel.getDescription());
        taskEntity.setState(taskModel.getState());
        taskEntity.setPriority(taskModel.getPriority());
        taskEntity.setLimitDate(taskModel.getLimitDate());
        taskEntity.setAssignedUser(mapUserModelToEntity(taskModel.getAssignedUserId())); // Mapeo de usuario asignado
        return taskEntity;
    }

    // 🔥 Métodos auxiliares para RoleEntity <-> RoleModel
    @Named("mapRoleEntityToModel")
    default RoleModel mapRoleEntityToModel(RoleEntity roleEntity) {
        if (roleEntity == null) return null;
        RoleModel roleModel = new RoleModel();
        roleModel.setId(roleEntity.getId());
        roleModel.setName(roleEntity.getName());
        return roleModel;
    }

    @Named("mapRoleModelToEntity")
    default RoleEntity mapRoleModelToEntity(RoleModel roleModel) {
        if (roleModel == null) return null;
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(roleModel.getId());
        roleEntity.setName(roleModel.getName());
        return roleEntity;
    }
}
