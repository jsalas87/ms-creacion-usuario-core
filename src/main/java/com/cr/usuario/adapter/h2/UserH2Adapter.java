package com.cr.usuario.adapter.h2;

import com.cr.usuario.application.port.out.UserRepository;
import com.cr.usuario.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Slf4j
@Component
public class UserH2Adapter implements UserRepository {

    private final UserH2Repository userH2Repository;

    public UserH2Adapter(UserH2Repository userH2Repository) {
        this.userH2Repository = userH2Repository;
    }

    @Override
    public User insertUser(User user) {
        return userH2Repository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
       return userH2Repository.findByEmail(email);
    }
}
