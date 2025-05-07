package com.sg.sgapicommon.model.vo.analysis;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author WSG
 */
@Data
public class InterfaceInfoTotalCountVO implements Serializable {

    private Long id;

    private String name;

    private Long totalInvokes;
}
