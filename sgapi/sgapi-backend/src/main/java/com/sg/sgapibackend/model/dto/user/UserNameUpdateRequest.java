package com.sg.sgapibackend.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @author WSG
 */
@Data
public class UserNameUpdateRequest implements Serializable {

    private final Long SerialVersionUID = 1L;

    private Long id;

    private String userName;
}
