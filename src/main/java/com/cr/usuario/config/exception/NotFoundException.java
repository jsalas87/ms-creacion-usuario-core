package com.cr.usuario.config.exception;


import com.cr.usuario.config.ErrorCode;

public class NotFoundException extends GenericException{

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
