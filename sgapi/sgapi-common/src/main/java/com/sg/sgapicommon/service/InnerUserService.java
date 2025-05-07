package com.sg.sgapicommon.service;

import com.sg.sgapicommon.model.entity.User;

/**
 * 用户服务
 *
 * @author WSG
 * 
 */
public interface InnerUserService {

    /**
     * 获取数据库中是否已分配给用户密钥（secretId）
     */
    User getInvokeUser(String secretId);
}
