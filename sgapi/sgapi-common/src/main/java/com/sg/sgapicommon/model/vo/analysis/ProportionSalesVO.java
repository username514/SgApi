package com.sg.sgapicommon.model.vo.analysis;

import com.sg.sgapicommon.model.entity.InterfaceInfoProportion;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProportionSalesVO implements Serializable {

    public static final Long SerialVersionUID = 1L;

    private List<InterfaceInfoProportion> interfaceInfoProportionList;
}
