package com.yt.plugin.quartz.task;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

//@Component
public class TestJob3 extends BaseStatelessTask {
    Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void execute(JobDataMap jobDataMap) {

        log.info("执行自定义计划任务3,当前时间:{}", new Date());

    }

    @Override
    public void setParamsList(List<String> paramsMap) {

    }



}
