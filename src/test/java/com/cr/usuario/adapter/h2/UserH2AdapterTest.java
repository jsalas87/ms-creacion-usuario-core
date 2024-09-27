package com.cr.usuario.adapter.h2;

import static com.cr.usuario.domain.Mock.mockUser;
import static com.cr.usuario.domain.Mock.mockUserRequestModel;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cr.usuario.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserH2AdapterTest {

    @Mock
    private UserH2Repository userH2Repository;

    @InjectMocks
    private UserH2Adapter userH2Adapter;

    private User mockUser;
    private User mockUserOut;

    @BeforeEach
    void setUp() {
        mockUser = mockUserRequestModel().toDomain();
        mockUserOut = mockUser();
    }

    @Test
    @DisplayName("should insert user successfully")
    void shouldInsertUserSuccessfully() {
        when(userH2Repository.save(any(User.class))).thenReturn(mockUserOut);

        User result = userH2Adapter.insertUser(mockUser);

        verify(userH2Repository).save(mockUser);
        assertThat(result).isEqualTo(mockUserOut);
    }

    @Test
    @DisplayName("should find user by email successfully")
    void shouldFindUserByEmailSuccessfully() {
        when(userH2Repository.findByEmail(anyString())).thenReturn(mockUserOut);

        User result = userH2Adapter.findUserByEmail(mockUser.getEmail());

        verify(userH2Repository).findByEmail(mockUser.getEmail());
        assertThat(result).isEqualTo(mockUserOut);
    }
}

