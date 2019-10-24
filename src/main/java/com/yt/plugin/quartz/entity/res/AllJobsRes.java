package com.yt.plugin.quartz.entity.res;

import com.yt.plugin.quartz.task.BaseTask;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 所有job信息res
 * @Author zhuzhengjie 2019年03月02日20:20:11
 *
 */
public class AllJobsRes implements Serializable {
    @ApiModelProperty(required = true, notes = "计划任务名称")
    private String jobName;
    @ApiModelProperty(required = true, notes = "计划任务所属组")
    private String jobGroupName;
    @ApiModelProperty(required = true, notes = "描述")
    private String description;
    @ApiModelProperty(required = true, notes = "状态")
    private String jobStatus;
    @ApiModelProperty(required = true, notes = "cron表达式")
    private String jobCron;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }


    public static void  main(String []args){
        String clazz = "com.yt.plugin.quartz.task.BaseTask";
        try {
            Class<? extends BaseTask> clz = (Class<? extends BaseTask>) Class.forName(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
