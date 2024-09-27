package com.cr.usuario.config;

public enum ErrorCode {
    WEB_CLIENT_GENERIC(100, "Error del Web Client"),
    EMAIL_EXIST(101, "El correo ya está registrado"),
    INVALID_TOKEN(102, "Token invalido"),
    UNAUTHORIZED(103, "Acceso no autorizado"),
    NOT_FOUND(102, "Usuario no encontrado");

    private final int value;
    private final String reasonPhrase;

    ErrorCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
