package com.cr.usuario.application.service;

import com.cr.usuario.application.port.out.UserRepository;
import com.cr.usuario.config.ErrorCode;
import com.cr.usuario.config.exception.BadRequestException;
import com.cr.usuario.domain.User;
import com.cr.usuario.config.security.JwtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public User createUser(User user) {

        var userExist = userRepository.findUserByEmail(user.getEmail());
        if(Optional.ofNullable(userExist).isPresent() && Optional.ofNullable(userExist.getId()).isPresent()) {
            throw new BadRequestException(ErrorCode.EMAIL_EXIST);
        }

        user.setId(UUID.randomUUID());
        user.setToken(jwtService.generateToken(user.getEmail()));
        var now = new Timestamp(System.currentTimeMillis());
        user.setCreated(now);
        user.setModified(now);
        user.setLastLogin(now);
        user.setIsActive(true);
        if (Optional.ofNullable(user.getPhones()).isPresent() && !user.getPhones().isEmpty()) {
            user.getPhones().forEach(phone -> phone.setUser(user));
        }
        return userRepository.insertUser(user);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
