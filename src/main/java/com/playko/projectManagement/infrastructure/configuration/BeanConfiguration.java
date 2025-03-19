package com.playko.projectManagement.infrastructure.configuration;

import com.playko.projectManagement.application.handler.IEmailHandler;
import com.playko.projectManagement.application.handler.IProjectHandler;
import com.playko.projectManagement.application.handler.impl.EmailHandler;
import com.playko.projectManagement.domain.api.IBoardColumnServicePort;
import com.playko.projectManagement.domain.api.IBoardServicePort;
import com.playko.projectManagement.domain.api.ICommentServicePort;
import com.playko.projectManagement.domain.api.IEmailServicePort;
import com.playko.projectManagement.domain.api.IPdfServicePort;
import com.playko.projectManagement.domain.api.IProjectServicePort;
import com.playko.projectManagement.domain.api.IRoleServicePort;
import com.playko.projectManagement.domain.api.ISubTaskServicePort;
import com.playko.projectManagement.domain.api.ITaskServicePort;
import com.playko.projectManagement.domain.api.ITeamServicePort;
import com.playko.projectManagement.domain.api.IUserServicePort;
import com.playko.projectManagement.domain.spi.IAuthPasswordEncoderPort;
import com.playko.projectManagement.domain.spi.IBoardColumnPersistencePort;
import com.playko.projectManagement.domain.spi.IBoardPersistencePort;
import com.playko.projectManagement.domain.spi.ICommentPersistencePort;
import com.playko.projectManagement.domain.spi.IEmailPersistencePort;
import com.playko.projectManagement.domain.spi.IPdfPersistencePort;
import com.playko.projectManagement.domain.spi.IProjectPersistencePort;
import com.playko.projectManagement.domain.spi.IRolePersistencePort;
import com.playko.projectManagement.domain.spi.ISubTaskPersistencePort;
import com.playko.projectManagement.domain.spi.ITaskPersistencePort;
import com.playko.projectManagement.domain.spi.ITeamPersistencePort;
import com.playko.projectManagement.domain.spi.IUserPersistencePort;
import com.playko.projectManagement.domain.usecase.BoardColumnUseCase;
import com.playko.projectManagement.domain.usecase.BoardUseCase;
import com.playko.projectManagement.domain.usecase.CommentUseCase;
import com.playko.projectManagement.domain.usecase.EmailUseCase;
import com.playko.projectManagement.domain.usecase.PdfUseCase;
import com.playko.projectManagement.domain.usecase.ProjectUseCase;
import com.playko.projectManagement.domain.usecase.RoleUseCase;
import com.playko.projectManagement.domain.usecase.SubTaskUseCase;
import com.playko.projectManagement.domain.usecase.TaskUseCase;
import com.playko.projectManagement.domain.usecase.TeamUseCase;
import com.playko.projectManagement.domain.usecase.UserUseCase;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.BoardColumnJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.BoardJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.CommentJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.EmailJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.PdfJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.ProjectJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.SubTaskJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.TaskJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.TeamJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.adapter.UserJpaAdapter;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IBoardColumnEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IBoardEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.ICommentEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IProjectEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IRoleEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.ISubTaskEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.ITaskEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.ITeamEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.mapper.IUserEntityMapper;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IBoardColumnRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IBoardRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ICommentRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IProjectRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IRoleRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ISubTaskRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ITaskRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.ITeamRepository;
import com.playko.projectManagement.infrastructure.output.jpa.repository.IUserRepository;
import com.playko.projectManagement.shared.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.TemplateEngine;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    @Bean
    public IAuthPasswordEncoderPort authPasswordEncoderPort() {
        return new AuthBcryptAdapter(encoder());
    }
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IUserPersistencePort userPersistencePort(IUserRepository userRepository, IUserEntityMapper userEntityMapper, IRoleRepository roleRepository) {
        return new UserJpaAdapter(userRepository, userEntityMapper, roleRepository);
    }

    @Bean
    public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort) {
        return new UserUseCase(userPersistencePort, rolePersistencePort, authPasswordEncoderPort());
    }

    @Bean
    public IRoleServicePort roleServicePort(IRolePersistencePort rolePersistencePort) {
        return new RoleUseCase(rolePersistencePort);
    }

    @Bean
    public IRolePersistencePort rolePersistencePort(IRoleRepository roleRepository, IRoleEntityMapper roleEntityMapper) {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }

    @Bean
    public ITeamPersistencePort teamPersistencePort(ITeamRepository teamRepository, ITeamEntityMapper teamEntityMapper, IUserRepository userRepository, EmailHandler emailHandler) {
        return new TeamJpaAdapter(teamRepository, teamEntityMapper, userRepository, emailHandler);
    }

    @Bean
    public ITeamServicePort teamServicePort(ITeamPersistencePort teamPersistencePort) {
        return new TeamUseCase(teamPersistencePort);
    }

    @Bean
    public ITaskPersistencePort taskPersistencePort(ITaskRepository taskRepository, IProjectRepository projectRepository,
                                                    IBoardColumnRepository boardColumnRepository, IUserRepository userRepository,
                                                    ITaskEntityMapper taskEntityMapper, IEmailHandler emailHandler,
                                                    SecurityUtils securityUtils, IBoardRepository boardRepository) {
        return new TaskJpaAdapter(taskRepository, projectRepository, boardColumnRepository, userRepository, taskEntityMapper, emailHandler, securityUtils, boardRepository);
    }

    @Bean
    public ITaskServicePort taskServicePort(ITaskPersistencePort taskPersistencePort) {
        return new TaskUseCase(taskPersistencePort);
    }

    @Bean
    public ISubTaskPersistencePort subTaskPersistencePort(ISubTaskRepository subTaskRepository, ITaskRepository taskRepository,
                                                          ISubTaskEntityMapper subTaskEntityMapper, IEmailHandler emailHandler,
                                                          SecurityUtils securityUtils) {
        return new SubTaskJpaAdapter(subTaskRepository, taskRepository, subTaskEntityMapper, emailHandler, securityUtils);
    }

    @Bean
    public ISubTaskServicePort subTaskServicePort(ISubTaskPersistencePort subTaskPersistencePort) {
        return new SubTaskUseCase(subTaskPersistencePort);
    }

    @Bean
    public IProjectPersistencePort projectPersistencePort(IProjectRepository projectRepository, IProjectEntityMapper projectEntityMapper,
                                                          IUserRepository userRepository, IRoleRepository roleRepository, IEmailHandler emailHandler,
                                                          SecurityUtils securityUtils) {
        return new ProjectJpaAdapter(projectRepository, projectEntityMapper, userRepository, roleRepository, emailHandler, securityUtils);
    }

    @Bean
    public IProjectServicePort projectServicePort(IProjectPersistencePort projectPersistencePort) {
        return new ProjectUseCase(projectPersistencePort);
    }

    @Bean
    public IEmailPersistencePort emailPersistencePort(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        return new EmailJpaAdapter(javaMailSender, templateEngine);
    }

    @Bean
    public IEmailServicePort emailServicePort(IEmailPersistencePort emailPersistencePort) {
        return new EmailUseCase(emailPersistencePort);
    }

    @Bean
    public ICommentPersistencePort commentPersistencePort(ICommentRepository commentRepository, IUserRepository userRepository,
                                                          ITaskRepository taskRepository, ICommentEntityMapper commentEntityMapper) {
        return new CommentJpaAdapter(commentRepository, userRepository, taskRepository, commentEntityMapper);
    }

    @Bean
    public ICommentServicePort commentServicePort(ICommentPersistencePort commentPersistencePort) {
        return new CommentUseCase(commentPersistencePort);
    }

    @Bean
    public IBoardPersistencePort boardPersistencePort(IBoardRepository boardRepository, IBoardEntityMapper boardEntityMapper,ITaskRepository taskRepository, IBoardColumnRepository boardColumnRepository, IProjectRepository projectRepository) {
        return new BoardJpaAdapter(boardRepository, boardEntityMapper, taskRepository, boardColumnRepository, projectRepository);
    }
    @Bean
    public IBoardServicePort boardServicePort(IBoardPersistencePort boardPersistencePort) {
        return new BoardUseCase(boardPersistencePort);
    }

    @Bean
    public IBoardColumnPersistencePort boardColumnPersistencePort(IBoardRepository boardRepository, IBoardColumnRepository boardColumnRepository, IBoardColumnEntityMapper boardColumnEntityMapper) {
        return new BoardColumnJpaAdapter(boardRepository, boardColumnRepository, boardColumnEntityMapper);
    }

    @Bean
    public IBoardColumnServicePort boardColumnServicePort(IBoardColumnPersistencePort boardColumnPersistencePort) {
        return new BoardColumnUseCase(boardColumnPersistencePort);
    }

    @Bean
    public IPdfPersistencePort pdfPersistencePort(IProjectHandler projectHandler) {
        return new PdfJpaAdapter(projectHandler);
    }

    @Bean
    public IPdfServicePort pdfServicePort(IPdfPersistencePort pdfPersistencePort) {
        return new PdfUseCase(pdfPersistencePort);
    }
}
