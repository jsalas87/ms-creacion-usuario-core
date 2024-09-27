package com.cr.usuario.application.port.out;

import com.cr.usuario.domain.User;

public interface UserRepository {
    User insertUser(User user);
    User findUserByEmail(String email);
}
