package com.sg.sgapibackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录（手机号）
 * @author WSG
 */
@Data
public class UserLoginByEmailRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;

    private String verificationCode;

}
