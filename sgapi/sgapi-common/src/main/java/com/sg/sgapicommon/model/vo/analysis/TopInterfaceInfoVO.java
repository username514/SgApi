package com.sg.sgapicommon.model.vo.analysis;

import com.sg.sgapicommon.model.entity.InterfaceLogWeekCount;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author WSG
 */
@Data
public class TopInterfaceInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 接口调用数（一周）
     */
    private List<InterfaceLogWeekCount> interfaceLogWeekCounts;

    /**
     * 接口调用排行（）
     */
    private List<InterfaceInfoTotalCountVO> interfaceInfoTotalCount;

    /**
     * 最受欢迎
     */
    private String mostPopular;
}
