package com.cr.usuario.application.port.in;

import com.cr.usuario.domain.User;

import java.util.UUID;

public interface FindUserQuery {

    User execute(String email);
}
