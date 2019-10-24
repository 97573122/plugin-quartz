package com.yt.plugin.quartz.task;

import org.quartz.Job;

import java.util.List;

/**
 * quartz业务扩展接口
 * @Author zhuzhengjie
 */
public interface BaseTask extends Job {

    public List<String> getParamsList();

}
