package com.cr.usuario.application.service;

import static com.cr.usuario.domain.Mock.mockUser;
import static com.cr.usuario.domain.Mock.mockUserRequestModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


import com.cr.usuario.application.port.out.UserRepository;
import com.cr.usuario.config.ErrorCode;
import com.cr.usuario.config.exception.BadRequestException;
import com.cr.usuario.config.security.JwtService;
import com.cr.usuario.domain.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    private User mockUser;
    private User mockUserOut;

    @BeforeEach
    void setUp() {
        // Inicializa un objeto User de prueba
        mockUser = mockUserRequestModel().toDomain();
        mockUserOut = mockUser();
    }

    @Test
    @DisplayName("should create user successfully")
    void shouldCreateUserSuccessfully() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);
        when(jwtService.generateToken(anyString())).thenReturn("The Token");
        when(userRepository.insertUser(any(User.class))).thenReturn(mockUserOut);

        User result = userService.createUser(mockUser);

        verify(userRepository).findUserByEmail(mockUser.getEmail());
        verify(jwtService).generateToken(mockUser.getEmail());
        verify(userRepository).insertUser(mockUser);

        assertThat(result).isEqualTo(mockUserOut);
    }

    @Test
    @DisplayName("should throw BadRequestException when email already exists")
    void shouldThrowBadRequestExceptionWhenEmailExists() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(mockUserOut);

        assertThatThrownBy(() -> userService.createUser(mockUser))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(ErrorCode.EMAIL_EXIST.getReasonPhrase());

        verify(userRepository).findUserByEmail(mockUser.getEmail());
        verify(userRepository, never()).insertUser(any(User.class));
    }

    @Test
    @DisplayName("should find user by email successfully")
    void shouldFindUserByEmailSuccessfully() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(mockUser);

        User result = userService.findByEmail(mockUser.getEmail());

        verify(userRepository).findUserByEmail(mockUser.getEmail());
        assertThat(result).isEqualTo(mockUser);
    }
}

