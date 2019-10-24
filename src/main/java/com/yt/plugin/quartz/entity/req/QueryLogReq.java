package com.yt.plugin.quartz.entity.req;

import com.yt.plugin.quartz.entity.page.AbstractPageEntity;
import io.swagger.annotations.ApiModelProperty;

public class QueryLogReq extends AbstractPageEntity {
    @ApiModelProperty(required = true, notes = "计划任务名称")
    private String jobName;

    @ApiModelProperty(required = true, notes = "计划任务组名称")
    private String jobGroup;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
}
