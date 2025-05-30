package com.sg.sgapiclientsdk.model.params;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 * @author WSG
 */
@Data
public class UserParams implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
}
