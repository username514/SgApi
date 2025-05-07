package com.sg.sgapibackend.service.impl.inner;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import com.sg.sgapibackend.common.ErrorCode;
import com.sg.sgapibackend.exception.BusinessException;
import com.sg.sgapibackend.service.InterfaceLogService;
import com.sg.sgapicommon.model.entity.InterfaceLog;
import com.sg.sgapicommon.service.InnerInterfaceLogService;

/**
 * @author WSG
 */
@DubboService
@Slf4j
public class InnerInterfaceLogServiceImpl implements InnerInterfaceLogService {

    @Autowired
    InterfaceLogService interfaceLogService;

    @Override
    public boolean save(InterfaceLog interfaceLog) {
        if(interfaceLog == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "interfaceLog为空");
        }
        interfaceLogService.validInterfaceLog(interfaceLog, true);
        log.info("存储接口调用日志：",interfaceLog);
        return interfaceLogService.save(interfaceLog);
    }
}
