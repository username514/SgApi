package com.sg.sgapibackend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sg.sgapibackend.annotation.AuthCheck;
import com.sg.sgapibackend.common.BaseResponse;
import com.sg.sgapibackend.common.ResultUtils;
import com.sg.sgapibackend.constant.UserConstant;
import com.sg.sgapibackend.service.*;
import com.sg.sgapicommon.model.entity.*;
import com.sg.sgapicommon.model.vo.analysis.*;

import javax.annotation.Resource;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sg.sgapibackend.constant.RedisConstants.LOGIN_TOKEN_KEY;
import static com.sg.sgapibackend.constant.RedisConstants.SYSTEM_PV_KEY;

/**
 * 运行分析控制器
 *
 * @author WSG
 * 
 */
@RestController
@RequestMapping("/analysis")
@Slf4j
public class AnalyseController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Resource
    private InterfaceLogService interfaceLogService;

    @Resource
    private InterfaceLogWeekCountService interfaceLogWeekCountService;

    @Resource
    private InterfaceInfoProportionService interfaceInfoProportionService;

    @GetMapping("/interface/introduceRow")
    public BaseResponse<IntroduceRowVO> getInterfaceIntroduceRow() {

        IntroduceRowVO introduceRowVO = new IntroduceRowVO();

        // 获取接口调用次数
        Long interfaceInfoCount = interfaceInfoService.getInterfaceInfoTotalInvokesCount();
        // 获取最近接口调用平均时间
        Integer cost = interfaceLogService.getInterfaceInfoAverageCost();
        introduceRowVO.setInterfaceInfoCount(interfaceInfoCount);
        introduceRowVO.setCost(cost);
        return ResultUtils.success(introduceRowVO);
    }

    @GetMapping("/introduceRow")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<IntroduceRowVO> getIntroduceRow() {
        Long size = stringRedisTemplate.opsForHyperLogLog().size(SYSTEM_PV_KEY);
        // 获取PV
        IntroduceRowVO introduceRowVO = new IntroduceRowVO();
        introduceRowVO.setPv(size);

        // 用户数
        Set<String> keys = stringRedisTemplate.keys( LOGIN_TOKEN_KEY + "*");
        if (ObjectUtil.isEmpty(keys)) {
            introduceRowVO.setOnLineUserCount(0L);
        }
        introduceRowVO.setOnLineUserCount(Long.valueOf(keys.size()));

        // 获取接口调用次数
        Long interfaceInfoCount = interfaceInfoService.getInterfaceInfoTotalInvokesCount();
        // 获取最近接口调用平均时间
        Integer cost = interfaceLogService.getInterfaceInfoAverageCost();
        introduceRowVO.setInterfaceInfoCount(interfaceInfoCount);
        introduceRowVO.setCost(cost);



        return ResultUtils.success(introduceRowVO);
    }

    @GetMapping("/topInterfaceInfo")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<TopInterfaceInfoVO> getTopInterfaceInfo() {
        TopInterfaceInfoVO topInterfaceInfoVO = new TopInterfaceInfoVO();
        List<InterfaceLogWeekCount> interfaceLogWeekCountList = interfaceLogWeekCountService.list();
        List<InterfaceInfo> interfaceInfoList = interfaceInfoService.list();
        List<InterfaceInfoTotalCountVO> interfaceInfoTotalCountVOList = interfaceInfoList.stream().map(
                interfaceInfo -> BeanUtil.copyProperties(interfaceInfo, InterfaceInfoTotalCountVO.class)
        ).collect(Collectors.toList());
        topInterfaceInfoVO.setInterfaceInfoTotalCount(interfaceInfoTotalCountVOList);
        topInterfaceInfoVO.setInterfaceLogWeekCounts(interfaceLogWeekCountList);
        topInterfaceInfoVO.setMostPopular(interfaceInfoList.get(0).getName());
        return ResultUtils.success(topInterfaceInfoVO);
    }

    @GetMapping("/interfaceinfoProportion")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<ProportionSalesVO> getInterfaceinfoProportion() {
        ProportionSalesVO proportionSalesVO = new ProportionSalesVO();
        List<InterfaceInfoProportion> interfaceInfoProportionList = interfaceInfoProportionService.list();
        proportionSalesVO.setInterfaceInfoProportionList(interfaceInfoProportionList);
        return ResultUtils.success(proportionSalesVO);
    }

}
