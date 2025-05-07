package com.sg.sgapibackend.service.impl.inner;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import com.sg.sgapibackend.common.ErrorCode;
import com.sg.sgapibackend.exception.BusinessException;
import com.sg.sgapibackend.service.impl.UserServiceImpl;
import com.sg.sgapicommon.model.entity.User;
import com.sg.sgapicommon.service.InnerUserService;

/**
 * @author WSG
 */
@DubboService
@Slf4j
public class InnerUserServiceImpl implements InnerUserService {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 根据 secretId 查询用户
     * @param secretId
     * @return
     */
    @Override
    public User getInvokeUser(String secretId) {
        log.info("Dubbo调用查询getInvokeUser  secretId:{}",secretId);
        // 校验
        if(StrUtil.isBlank(secretId)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 封装查询
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getSecretId, secretId);

        //查询
        return userService.getOne(queryWrapper);
    }
}
