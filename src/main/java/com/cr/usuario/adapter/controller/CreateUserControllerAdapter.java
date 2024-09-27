package com.cr.usuario.adapter.controller;

import brave.Tracer;
import com.cr.usuario.adapter.controller.model.ClassRequestProcessor;
import com.cr.usuario.adapter.controller.model.ResponseRest;
import com.cr.usuario.adapter.controller.model.UserRequestModel;
import com.cr.usuario.adapter.controller.model.UserResponseModel;
import com.cr.usuario.application.port.in.CreateUserCommand;
import com.cr.usuario.application.port.in.FindUserQuery;
import com.cr.usuario.config.validation.ValidationConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1.0/cr/user")
public class CreateUserControllerAdapter {

    private final Tracer tracer;
    private final ClassRequestProcessor processor;
    private final CreateUserCommand createUserCommand;
    private final FindUserQuery findUserQuery;
    private final ValidationConfig validationConfig;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseRest<UserResponseModel> postUser(
            HttpServletRequest httpServletRequest,
            @Valid @RequestBody UserRequestModel userRequest
            ) {
        log.info("REGEX "+validationConfig.getPassword().getRegex());
        userRequest.validate(validationConfig.getPassword().getRegex(), validationConfig.getPassword().getMsg());
        log.info("Se llamo al servicio crear usuarios para el usuario email: {}", userRequest.getEmail());
        var user = createUserCommand.execute(userRequest.toDomain());
        return processor.processRequest(
                httpServletRequest,
                tracer,
                () -> UserResponseModel.of(user)
        );
    }

    @GetMapping("/get")
    public UserResponseModel getUser(@RequestHeader("user-email") String email) {
        return UserResponseModel.of(findUserQuery.execute(email));
    }
}
