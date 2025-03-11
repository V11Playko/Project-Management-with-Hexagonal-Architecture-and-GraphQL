package com.playko.projectManagement.infrastructure.output.jpa.adapter;

import com.playko.projectManagement.application.dto.request.EmailRequestDto;
import com.playko.projectManagement.application.handler.IEmailHandler;
import com.playko.projectManagement.domain.model.SubTaskModel;
import com.playko.projectManagement.domain.spi.ISubTaskPersistencePort;
import com.playko.projectManagement.infrastructure.exception.SubTaskNotFoundException;
import com.playko.projectManagement.infrastructure.exception.TaskNotFoundException;
import com.playko.projectManagement.infrastructure.output.jpa.entity.SubTaskEntity;
import com.playko.projectManagement.infrastructure.output.jpa.entity.TaskEntity;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.ISubTaskEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ISubTaskRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ITaskRepository;
import com.playko.projectManagement.shared.enums.SubTaskState;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SubTaskJpaAdapter implements ISubTaskPersistencePort {
    private final ISubTaskRepository subTaskRepository;
    private final ITaskRepository taskRepository;
    private final ISubTaskEntityMapper subTaskEntityMapper;
    private final IEmailHandler emailHandler;
    @Override
    public void addSubTask(Long taskId, SubTaskModel subTaskModel) {
        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        SubTaskEntity subTaskEntity = subTaskEntityMapper.toEntity(subTaskModel);
        subTaskEntity.setTask(taskEntity);

        subTaskRepository.save(subTaskEntity);
    }

    @Override
    public List<SubTaskModel> getSubTasksByTask(Long taskId) {
        return subTaskEntityMapper.toModelList(subTaskRepository.findByTaskId(taskId));
    }

    @Override
    public void updateSubTask(Long subTaskId, SubTaskModel subTaskModel) {
        SubTaskEntity subTaskEntity = subTaskRepository.findById(subTaskId)
                .orElseThrow(SubTaskNotFoundException::new);

        subTaskEntity.setTitle(subTaskModel.getTitle());
        subTaskEntity.setDescription(subTaskModel.getDescription());
        subTaskEntity.setState(SubTaskState.DONE);

        subTaskRepository.save(subTaskEntity);

        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setDestinatario(subTaskEntity.getTask().getAssignedUser().getEmail());
        emailRequestDto.setAsunto("Actualización de Subtarea");
        String message = String.format("Tu subtarea '%s' ha sido actualizada.",
                subTaskEntity.getTitle());
        emailRequestDto.setMensaje(message);
        emailHandler.sendEmail(emailRequestDto);
    }

    @Override
    public void deleteSubTask(Long subTaskId) {
        if (!subTaskRepository.existsById(subTaskId)) {
            throw new SubTaskNotFoundException();
        }
        subTaskRepository.deleteById(subTaskId);
    }
}
