package com.sg.sgapibackend.model.dto.user;

import lombok.Data;

/**
 * @author WSG
 */
@Data
public class UserLoginByXcxRequest {

    private static final long serialVersionUID = 1L;

    private String code;

    private String scene;
}
