package com.yt.plugin.quartz.task;


import com.yt.plugin.quartz.service.QuartzService;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

//@Component
public class TestJob extends BaseStatefulTask {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private QuartzService quartzService;

    private final String params_date = "date";

    public TestJob(){
        log.info("test job  init===========");
    }

    @Override
    public void execute(JobDataMap jobDataMap) {
        System.out.println(quartzService.getClass());
        log.info("执行自定义计划任务1,当前时间:{},pramas:{}", new Date(),jobDataMap.getString(params_date));

    }

    @Override
    public void setParamsList(List<String> paramsMap) {
        paramsMap.add(params_date);
    }


}
