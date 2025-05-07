package com.sg.sgapibackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sg.sgapicommon.model.entity.InterfaceLogWeekCount;
import com.sg.sgapibackend.service.InterfaceLogWeekCountService;
import com.sg.sgapibackend.mapper.InterfaceLogWeekCountMapper;
import org.springframework.stereotype.Service;

/**
* @author PYW
* @description 针对表【interface_log_week_count】的数据库操作Service实现
*
*/
@Service
public class InterfaceLogWeekCountServiceImpl extends ServiceImpl<InterfaceLogWeekCountMapper, InterfaceLogWeekCount>
    implements InterfaceLogWeekCountService{

}




