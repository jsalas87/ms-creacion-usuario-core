package com.cr.usuario.config.exception;


import com.cr.usuario.config.ErrorCode;

public class UnauthorizedException extends GenericException{

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
