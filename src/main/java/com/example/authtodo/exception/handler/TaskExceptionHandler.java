package com.example.authtodo.exception.handler;

import com.example.authtodo.exception.GeneralException;
import com.example.authtodo.global.BaseErrorCode;

public class TaskExceptionHandler extends GeneralException {

    public TaskExceptionHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
