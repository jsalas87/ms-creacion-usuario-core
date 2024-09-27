package com.cr.usuario.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = Mock.mockUser();
    }

    @Test
    void testObfuscatePassword_WhenPasswordIsNotEmpty() {
        String obfuscatedPassword = user.obfuscatePassword();
        assertNotNull(obfuscatedPassword, "La contraseña ofuscada no debe ser nula");
        assertEquals("*******", obfuscatedPassword, "La contraseña ofuscada debe tener la misma longitud que la real, compuesta por asteriscos");
    }

    @Test
    void testObfuscatePassword_WhenPasswordIsEmpty() {
        user.setPassword("");
        String obfuscatedPassword = user.obfuscatePassword();
        assertEquals("", obfuscatedPassword, "Si la contraseña está vacía, la ofuscación también debe devolver una cadena vacía");
    }

    @Test
    void testObfuscatePassword_WhenPasswordIsNull() {
        user.setPassword(null);
        String obfuscatedPassword = user.obfuscatePassword();
        assertEquals("", obfuscatedPassword, "Si la contraseña es null, la ofuscación debe devolver una cadena vacía");
    }
}

