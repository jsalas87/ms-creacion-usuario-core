package com.cr.usuario.adapter.h2;

import com.cr.usuario.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserH2Repository extends CrudRepository<User, UUID> {
    User findByEmail(String email);
}
