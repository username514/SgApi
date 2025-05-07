package com.sg.sgapibackend.common;

import java.io.Serializable;
import lombok.Data;
import com.sg.sgapibackend.common.ErrorCode;

/**
 * 通用返回类
 *
 * @param <T>
 * @author WSG
 * 
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
