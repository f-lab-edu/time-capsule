package com.project.timecapsule.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // 캡슐 제목
    TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "캡슐 제목은 필수입니다."),
    TITLE_TOO_LONG(HttpStatus.BAD_REQUEST, "캡슐 제목은 50자 이하여야 합니다."),

    // 캡슐 오픈 시각
    OPEN_AT_REQUIRED(HttpStatus.BAD_REQUEST, "캡슐 오픈 시각을 입력해야 합니다."),
    OPEN_AT_NOT_IN_FUTURE(HttpStatus.BAD_REQUEST, "캡슐 오픈 시각은 캡슐 생성 시각보다 미래여야 합니다."),
    OPEN_AT_TOO_FAR_IN_FUTURE(HttpStatus.BAD_REQUEST, "캡슐 오픈 시각은 캡슐 생성 시각으로부터 10년 이내여야 합니다."),

    // 멀티 캡슐 정원
    MAX_MEMBERS_OUT_OF_RANGE(HttpStatus.BAD_REQUEST, "멀티 캡슐 정원은 방장을 포함해 2명 이상 20명 이하여야 합니다."),

    // 묻기 예정 시각
    BURY_DEADLINE_AT_REQUIRED(HttpStatus.BAD_REQUEST, "묻기 예정 시각을 입력해야 합니다."),
    BURY_DEADLINE_AT_NOT_IN_FUTURE(HttpStatus.BAD_REQUEST, "묻기 예정 시각은 캡슐 생성 시각보다 미래여야 합니다."),
    BURY_DEADLINE_AT_TOO_FAR_IN_FUTURE(HttpStatus.BAD_REQUEST, "묻기 예정 시각은 캡슐 생성 시각으로부터 14일 이내여야 합니다."),
    BURY_DEADLINE_AT_NOT_BEFORE_OPEN_AT(HttpStatus.BAD_REQUEST, "묻기 예정 시각은 캡슐 오픈 시각보다 앞서야 합니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
