package com.sg.sgapiclientsdk.exception;

import lombok.Data;

/**
 * 异常响应
 * @author WSG
 */
@Data
public class ErrorResponse {

    private int code;

    private String message;
}