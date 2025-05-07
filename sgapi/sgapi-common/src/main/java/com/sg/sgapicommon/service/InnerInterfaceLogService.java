package com.sg.sgapicommon.service;

import com.sg.sgapicommon.model.entity.InterfaceLog;

public interface InnerInterfaceLogService {

    /**
     * 存储日志
     */
    boolean save(InterfaceLog interfaceLog);

}
