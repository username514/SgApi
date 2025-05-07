package com.sg.sgapibackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author WSG
 */
@Data
public class UserLoginXcxCheckRequest implements Serializable {

    private String scene;

    private static final long serialVersionUID = 1L;

}
