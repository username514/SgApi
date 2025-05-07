package com.sg.sgapibackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author WSG
 */
@Data
public class UserUpdatePasswordRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String oldPassword;

    private String newPassword;

    private String checkNewPassword;
}
