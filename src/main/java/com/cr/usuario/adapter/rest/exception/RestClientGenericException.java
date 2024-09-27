package com.cr.usuario.adapter.rest.exception;

import com.cr.usuario.config.ErrorCode;
import com.cr.usuario.config.exception.GenericException;

public final class RestClientGenericException extends GenericException {

    public RestClientGenericException(ErrorCode errorCode) {
        super(errorCode);
    }

}
