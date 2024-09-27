package com.cr.usuario.application.port.in;

import com.cr.usuario.domain.User;

public interface FindUserQuery {

    User execute(String email);
}
