package com.example.authtodo.exception;

import com.example.authtodo.global.BaseErrorCode;
import com.example.authtodo.global.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode errorCode;

    public ErrorReasonDTO getErrorReason() {
        return errorCode.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return errorCode.getReasonHttpStatus();
    }
}
