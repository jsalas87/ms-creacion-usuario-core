package com.cr.usuario.application.usecase;

import static com.cr.usuario.domain.Mock.mockUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cr.usuario.application.port.out.UserRepository;

import com.cr.usuario.config.ErrorCode;
import com.cr.usuario.config.exception.NotFoundException;
import com.cr.usuario.domain.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class FindUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FindUserUseCase findUserUseCase;

    private User mockUser;
    private String email;

    @BeforeEach
    void setUp() {
        mockUser = mockUser();
        email = mockUser.getEmail();
    }

    @Test
    @DisplayName("should find user by email successfully")
    void shouldFindUserByEmail() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(mockUser);
        User result = findUserUseCase.execute(email);
        verify(userRepository).findUserByEmail(email);
        assertThat(result).isEqualTo(mockUser);
    }

    @Test
    @DisplayName("should throw NotFoundException when user is not found")
    void shouldThrowNotFoundExceptionWhenUserNotFound() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);
        assertThatThrownBy(() -> findUserUseCase.execute(email))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND.getReasonPhrase());

        verify(userRepository).findUserByEmail(email);
    }

    @Test
    @DisplayName("should throw NotFoundException when user ID is null")
    void shouldThrowNotFoundExceptionWhenUserIdIsNull() {
        mockUser.setId(null);
        when(userRepository.findUserByEmail(anyString())).thenReturn(mockUser);

        assertThatThrownBy(() -> findUserUseCase.execute(email))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND.getReasonPhrase());

        verify(userRepository).findUserByEmail(email);
    }
}

