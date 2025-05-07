package com.sg.sgapiclientsdk.exception;

public class SgApiException extends Exception{

    private int code;

    public SgApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public SgApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public SgApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public SgApiException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
