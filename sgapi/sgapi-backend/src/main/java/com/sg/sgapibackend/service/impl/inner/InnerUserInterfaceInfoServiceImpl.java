package com.sg.sgapibackend.service.impl.inner;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.sg.sgapibackend.service.UserInterfaceInfoService;
import com.sg.sgapicommon.service.InnerUserInterfaceInfoService;

/**
 * @author WSG
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Autowired
    private UserInterfaceInfoService userInterfaceInfoService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean invoke(Long interfaceInfoId, Long userId) {
        return userInterfaceInfoService.invoke(interfaceInfoId, userId);
    }
}
