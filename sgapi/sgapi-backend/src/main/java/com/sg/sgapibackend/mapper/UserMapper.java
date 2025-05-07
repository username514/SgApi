package com.sg.sgapibackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.sg.sgapicommon.model.entity.User;

/**
 * 用户数据库操作
 *
 * @author WSG
 * 
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




