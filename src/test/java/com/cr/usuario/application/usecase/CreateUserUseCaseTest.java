package com.cr.usuario.application.usecase;

import static com.cr.usuario.domain.Mock.mockUserRequestModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cr.usuario.application.service.UserService;
import com.cr.usuario.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = mockUserRequestModel().toDomain();
    }

    @Test
    @DisplayName("should call createUser method of UserService with given user")
    void shouldCallCreateUserService() {
        when(userService.createUser(any(User.class))).thenReturn(mockUser);
        User result = createUserUseCase.execute(mockUser);
        verify(userService).createUser(mockUser);
        assertThat(result).isEqualTo(mockUser);
    }
}
