package com.yt.plugin.quartz.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.quartz.Job;

/**
 * 新增任务
 */
@ApiModel(description = "新增计划任务入参")
public class AddJobReq extends BaseReq {

    @ApiModelProperty(required = true, notes = "计划任务类名,eg:com.yt.plugin.quartz.task.TestJob")
    private String jobClazz;

    @ApiModelProperty(required = true, notes = "计划任务运行时间cron表达式,eg:0/2 * * * * ?")
    private String cron;

    public String getJobClazz() {
        return jobClazz;
    }

    public void setJobClazz(String jobClazz) {
        this.jobClazz = jobClazz;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
