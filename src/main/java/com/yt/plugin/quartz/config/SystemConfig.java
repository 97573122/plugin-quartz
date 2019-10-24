package com.yt.plugin.quartz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhuzhengjie
 */
@Component
@ConfigurationProperties(prefix = "quartz")
public class SystemConfig {

    /**
     * 计划任务初始化运行表达式
     */
	private String defaultJobcron;

    /**
     * 计划任务初始化组名
     */
    private String defaultGroupName;

    public String getDefaultJobcron() {
        return defaultJobcron;
    }

    public void setDefaultJobcron(String defaultJobcron) {
        this.defaultJobcron = defaultJobcron;
    }

    public String getDefaultGroupName() {
        return defaultGroupName;
    }

    public void setDefaultGroupName(String defaultGroupName) {
        this.defaultGroupName = defaultGroupName;
    }
}
