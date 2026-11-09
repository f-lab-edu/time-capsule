package com.project.timecapsule.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "캡슐 제목은 필수입니다."),
    TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "캡슐 제목은 50자 이하여야 합니다."),
    OPEN_AT_REQUIRED(HttpStatus.BAD_REQUEST, "캡슐 오픈 시각을 입력해야 합니다."),
    OPEN_AT_NOT_IN_FUTURE(HttpStatus.BAD_REQUEST, "캡슐 오픈 시각은 캡슐 생성 시각보다 미래여야 합니다."),
    OPEN_AT_TOO_FAR_IN_FUTURE(HttpStatus.BAD_REQUEST, "캡슐 오픈 시각은 캡슐 생성 시각으로부터 10년 이내여야 합니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
