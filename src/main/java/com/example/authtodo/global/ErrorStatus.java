package com.example.authtodo.global;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {


    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 내부 에러입니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증에 실패하였습니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "권한이 없습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "LOGIN404", "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "LOGIN400", "이미 존재하는 사용자입니다."),
    USER_PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "LOGIN400", "비밀번호가 일치하지 않습니다."),

    TASK_NOT_FOUND(HttpStatus.NOT_FOUND, "TASK404", "할일을 찾을 수 없습니다."),
    TASK_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "TASK400", "이미 존재하는 할일입니다."),
    TASK_NOT_AUTHORIZED(HttpStatus.FORBIDDEN, "TASK403", "본인의 할일이 아닙니다."),
    TASK_NOT_COMPLETED(HttpStatus.BAD_REQUEST, "TASK400", "할일이 완료되지 않았습니다."),
    TASK_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "TASK400", "이미 완료된 할일입니다."),
    TASK_NOT_COMPLETED_YET(HttpStatus.BAD_REQUEST, "TASK400", "할일이 완료되지 않았습니다."),
    TASK_NOT_AUTHORIZED_YET(HttpStatus.FORBIDDEN, "TASK403", "본인의 할일이 아닙니다."),
    TASK_NOT_AUTHORIZED_NOT_COMPLETED(HttpStatus.FORBIDDEN, "TASK403", "본인의 완료되지 않은 할일이 아닙니다."),
    TASK_NOT_AUTHORIZED_COMPLETED_YET(HttpStatus.FORBIDDEN, "TASK403", "본인의 완료된 할일이 아닙니다."),
    TASK_NOT_AUTHORIZED_NOT_COMPLETED_YET(HttpStatus.FORBIDDEN, "TASK403", "본인의 완료되지 않은 할일이 아닙니다."),

    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "LOGIN400", "유효하지 않은 Refresh Token 입니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "LOGIN400", "만료된 Refresh Token 입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
