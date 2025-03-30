package com.playko.projectManagement.infrastructure.exceptionhandler;

import com.playko.projectManagement.infrastructure.exception.BoardColumnNotFoundException;
import com.playko.projectManagement.infrastructure.exception.BoardNotFoundException;
import com.playko.projectManagement.infrastructure.exception.InvalidKeywordException;
import com.playko.projectManagement.infrastructure.exception.InvalidProjectStateException;
import com.playko.projectManagement.infrastructure.exception.InvalidTaskStateException;
import com.playko.projectManagement.infrastructure.exception.MessageNotSendException;
import com.playko.projectManagement.infrastructure.exception.NoUsersFoundException;
import com.playko.projectManagement.infrastructure.exception.ProjectAlreadyExistsException;
import com.playko.projectManagement.infrastructure.exception.ProjectNotFoundException;
import com.playko.projectManagement.infrastructure.exception.ProjectsNotFoundException;
import com.playko.projectManagement.infrastructure.exception.StatsNotFoundException;
import com.playko.projectManagement.infrastructure.exception.SubTaskNotFoundException;
import com.playko.projectManagement.infrastructure.exception.TaskNotFoundException;
import com.playko.projectManagement.infrastructure.exception.TeamNotFoundException;
import com.playko.projectManagement.infrastructure.exception.UnauthorizedException;
import com.playko.projectManagement.infrastructure.exception.UserAlreadyExistsException;
import com.playko.projectManagement.infrastructure.exception.UserAlreadyRestrictedException;
import com.playko.projectManagement.infrastructure.exception.UserNotFoundException;
import com.playko.projectManagement.infrastructure.exception.UserNotInTeamException;
import com.playko.projectManagement.infrastructure.exception.UserNotRestrictedException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.playko.projectManagement.shared.constants.Exceptions.BOARD_COLUMN_NOT_FOUND_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.BOARD_NOT_FOUND_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.EMAIL_NOT_SEND_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.INVALID_KEYWORD_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.INVALID_PROJECT_STATE_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.INVALID_TASK_STATE_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.PROJECTS_NOT_FOUND_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.PROJECT_ALREADY_EXISTS_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.PROJECT_NOT_FOUND_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.RESPONSE_MESSAGE_KEY;
import static com.playko.projectManagement.shared.constants.Exceptions.SUB_TASK_NOT_FOUND_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.TASK_NOT_FOUND_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.TEAM_NOT_FOUND_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.UNAUTHORIZED_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.USERS_NOT_FOUND_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.USER_ALREADY_EXISTS_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.USER_ALREADY_RESTRICTED_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.USER_NOT_FOUND_IN_TEAM_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.USER_NOT_FOUND_MESSAGE;
import static com.playko.projectManagement.shared.constants.Exceptions.USER_NOT_RESTRICTED_MESSAGE;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleBindExceptions(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, String>> unauthorizedException(
            UnauthorizedException unauthorizedException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, UNAUTHORIZED_MESSAGE));
    }

    @ExceptionHandler(NoUsersFoundException.class)
    public ResponseEntity<Map<String, String>> noUsersFoundException(
            NoUsersFoundException noUsersFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, USERS_NOT_FOUND_MESSAGE));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> userNotFoundException(
            UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, USER_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> userAlreadyExistsException(
            UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, USER_ALREADY_EXISTS_MESSAGE));
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<Map<String, String>> teamNotFoundException(
            TeamNotFoundException teamNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, TEAM_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(UserNotInTeamException.class)
    public ResponseEntity<Map<String, String>> userNotInTeamException(
            UserNotInTeamException userNotInTeamException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, USER_NOT_FOUND_IN_TEAM_MESSAGE));
    }
    @ExceptionHandler(ProjectAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> projectAlreadyExistsException(
            ProjectAlreadyExistsException projectAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, PROJECT_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<Map<String, String>> projectNotFoundException(
            ProjectNotFoundException projectNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, PROJECT_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(BoardColumnNotFoundException.class)
    public ResponseEntity<Map<String, String>> boardColumnNotFoundException(
            BoardColumnNotFoundException boardColumnNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, BOARD_COLUMN_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Map<String, String>> taskNotFoundException(
            TaskNotFoundException taskNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, TASK_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(InvalidProjectStateException.class)
    public ResponseEntity<Map<String, String>> invalidProjectStateException(
            InvalidProjectStateException invalidProjectStateException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, INVALID_PROJECT_STATE_MESSAGE));
    }
    @ExceptionHandler(SubTaskNotFoundException.class)
    public ResponseEntity<Map<String, String>> subTaskNotFoundException(
            SubTaskNotFoundException subTaskNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, SUB_TASK_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(MessageNotSendException.class)
    public ResponseEntity<Map<String, String>> messageNotSendException(
            MessageNotSendException messageNotSendException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, EMAIL_NOT_SEND_MESSAGE));
    }
    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<Map<String, String>> boardNotFoundException(
            BoardNotFoundException boardNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, BOARD_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(InvalidTaskStateException.class)
    public ResponseEntity<Map<String, String>> invalidTaskStateException(
            InvalidTaskStateException invalidTaskStateException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, INVALID_TASK_STATE_MESSAGE));
    }
    @ExceptionHandler(UserAlreadyRestrictedException.class)
    public ResponseEntity<Map<String, String>> userAlreadyRestrictedException(
            UserAlreadyRestrictedException userAlreadyRestrictedException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, USER_ALREADY_RESTRICTED_MESSAGE));
    }
    @ExceptionHandler(UserNotRestrictedException.class)
    public ResponseEntity<Map<String, String>> userNotRestrictedException(
            UserNotRestrictedException userNotRestrictedException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, USER_NOT_RESTRICTED_MESSAGE));
    }
    @ExceptionHandler(ProjectsNotFoundException.class)
    public ResponseEntity<Map<String, String>> projectsNotFoundException(
            ProjectsNotFoundException projectsNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, PROJECTS_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(StatsNotFoundException.class)
    public ResponseEntity<Map<String, String>> statsNotFoundException(
            StatsNotFoundException statsNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, statsNotFoundException.getMessage()));
    }
    @ExceptionHandler(InvalidKeywordException.class)
    public ResponseEntity<Map<String, String>> invalidKeywordException(
            InvalidKeywordException invalidKeywordException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, INVALID_KEYWORD_MESSAGE));
    }
}
