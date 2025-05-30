package com.sg.sgapicommon.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.google.gson.Gson;
import com.sg.sgapicommon.model.entity.InterfaceInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 接口信息视图
 *
 * @author WSG
 * 
 */
@Data
public class InterfaceInfoVO implements Serializable {

    private final static Gson GSON = new Gson();

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 响应参数
     */
    private String responseParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 点赞
     */
    private Integer liked;

    /**
     * 请求示例
     */
    private String requestExample;

    /**
     * 调用总次数
     */
    @OrderBy(asc=false)
    private Long totalInvokes;

    /**
     * 返回格式（如JSON等）
     */
    private String returnFormat;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    private Integer status;

    /**
     * 创建人
     */
    private Long userId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 包装类转对象
     *
     * @param interfaceInfoVO
     * @return
     */
    public static InterfaceInfo voToObj(InterfaceInfoVO interfaceInfoVO) {
        if (interfaceInfoVO == null) {
            return null;
        }
        InterfaceInfo interfaceInfo = new InterfaceInfo();
        BeanUtils.copyProperties(interfaceInfoVO, interfaceInfo);
        return interfaceInfo;
    }

    /**
     * 对象转包装类
     *
     * @param interfaceInfo
     * @return
     */
    public static InterfaceInfoVO objToVo(InterfaceInfo interfaceInfo) {
        if (interfaceInfo == null) {
            return null;
        }
        InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
        BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
        return interfaceInfoVO;
    }
}
