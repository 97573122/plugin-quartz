package com.yt.plugin.quartz.entity.res;

import org.quartz.Job;

import java.io.Serializable;

public class PluginJobRes implements Serializable {
    private String jobName;
    private String jobGroupName;
    private String jobCron;
    private Class<? extends Job> clazz;

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

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron;
    }

    public Class<? extends Job> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends Job> clazz) {
        this.clazz = clazz;
    }
}
