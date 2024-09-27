package com.cr.usuario.config.exception;


import com.cr.usuario.config.ErrorCode;

public class BadRequestException extends GenericException{

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
