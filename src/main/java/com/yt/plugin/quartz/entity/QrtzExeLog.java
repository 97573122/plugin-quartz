package com.yt.plugin.quartz.entity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class QrtzExeLog {
    @ApiModelProperty(required = true, notes = "主键id")
    private Integer id;

    @ApiModelProperty(required = true, notes = "任务组")
    private String jobGroup;

    @ApiModelProperty(required = true, notes = "任务名称")
    private String jobName;

    @ApiModelProperty(required = true, notes = "执行时间")
    private Date exeTime;

    @ApiModelProperty(required = true, notes = "执行ip")
    private String exeIp;

    @ApiModelProperty(required = true, notes = "耗时(单位:毫秒)")
    private Integer takeTimes;

    @ApiModelProperty(required = true, notes = "运行结果")
    private String remark;

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getExeTime() {
        return exeTime;
    }

    public void setExeTime(Date exeTime) {
        this.exeTime = exeTime;
    }

    public String getExeIp() {
        return exeIp;
    }

    public void setExeIp(String exeIp) {
        this.exeIp = exeIp == null ? null : exeIp.trim();
    }

    public Integer getTakeTimes() {
        return takeTimes;
    }

    public void setTakeTimes(Integer takeTimes) {
        this.takeTimes = takeTimes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}