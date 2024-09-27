package com.cr.usuario.application.usecase;

import com.cr.usuario.application.port.in.CreateUserCommand;
import com.cr.usuario.application.service.UserService;
import com.cr.usuario.domain.User;
import org.springframework.stereotype.Component;

@Component
public class CreateUserUseCase implements CreateUserCommand {

    private final UserService createUserService;
    public CreateUserUseCase(UserService createUserService) {
        this.createUserService = createUserService;
    }
    @Override
    public User execute(User user) {
        return createUserService.createUser(user);
    }
}
