package com.yt.plugin.quartz.entity.req;

import io.swagger.annotations.ApiModel;

import java.util.Map;

/**
 * 新增任务
 */
@ApiModel(description = "修改计划任务入参")
public class ExeJobReq extends BaseReq {
    private Map<String,Object> paramsMap;

    public Map<String, Object> getParamsMap() {
        return paramsMap;
    }

    public void setParamsMap(Map<String, Object> paramsMap) {
        this.paramsMap = paramsMap;
    }
}
