package com.leesumin.cafe.exception;

public enum ErrorEnum {
    REJECT_USE_POINT(401, "포인트가 부족하여 결제에 실패했습니다.");

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
