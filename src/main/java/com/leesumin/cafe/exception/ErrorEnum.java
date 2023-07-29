package com.leesumin.cafe.exception;

public enum ErrorEnum {
    NOT_FOUND_CUSTOMER(301, "해당 회원 정보가 없습니다."),
    EXISTS_USER(302, "이미 등록된 회원 정보입니다."),
    REJECT_USE_POINT(401, "포인트가 부족하여 결제에 실패했습니다."),
    // TODO 회원 정보, 포인트 통합
    NOT_FOUND_POINT(402, "해당 고객의 포인트 정보가 없습니다."),
    NOT_FOUND_MENU(501, "메뉴 정보가 없습니다."),
    EXISTS_MENU(502, "이미 등록된 메뉴입니다.");

    private final int code;
    private final String message;
    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
