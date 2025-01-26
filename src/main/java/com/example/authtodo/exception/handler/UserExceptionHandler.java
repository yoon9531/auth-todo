package com.example.authtodo.exception.handler;

import com.example.authtodo.exception.GeneralException;
import com.example.authtodo.global.BaseErrorCode;
import com.example.authtodo.global.ErrorStatus;

public class UserExceptionHandler extends GeneralException {

    public UserExceptionHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
