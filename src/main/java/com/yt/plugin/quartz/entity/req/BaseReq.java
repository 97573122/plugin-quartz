package com.yt.plugin.quartz.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "基础入参")
public class BaseReq {
    @ApiModelProperty(required = true, notes = "计划任务名称")
    private String name;
    @ApiModelProperty(required = true, notes = "计划任务组名称")
    private String group;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
