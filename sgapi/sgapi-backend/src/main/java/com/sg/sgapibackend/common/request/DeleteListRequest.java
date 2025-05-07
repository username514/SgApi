package com.sg.sgapibackend.common.request;


import lombok.Data;

import java.util.List;

/**
 * @author WSG
 */
@Data
public class DeleteListRequest {
    /**
     * id组
     */
    private List<Long> ids;

    private static final long serialVersionUID = 1L;
}
