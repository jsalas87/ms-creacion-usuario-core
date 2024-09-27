package com.cr.usuario.application.port.in;

import com.cr.usuario.domain.User;

public interface CreateUserCommand {

    User execute(User user);
}
