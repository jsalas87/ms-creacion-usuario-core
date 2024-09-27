package com.cr.usuario.application.usecase;

import com.cr.usuario.application.port.in.FindUserQuery;
import com.cr.usuario.application.port.out.UserRepository;
import com.cr.usuario.config.ErrorCode;
import com.cr.usuario.config.exception.NotFoundException;
import com.cr.usuario.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class FindUserUseCase implements FindUserQuery {

    private final UserRepository userRepository;

    public FindUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User execute(String email) {
        log.info("Se va a buscar el usuario con email {}", email);
        var user = userRepository.findUserByEmail(email);
        if(Optional.ofNullable(user).isPresent() && Optional.ofNullable(user.getId()).isPresent()) {
            log.info("Se encontró el usuario con id {} e email {}", user.getId(), email);
            return user;
        } else {
            throw new NotFoundException(ErrorCode.NOT_FOUND);
        }
    }
}
