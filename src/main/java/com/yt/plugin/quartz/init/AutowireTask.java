package com.yt.plugin.quartz.init;

import com.yt.plugin.quartz.config.SystemConfig;
import com.yt.plugin.quartz.entity.res.PluginJobRes;
import com.yt.plugin.quartz.factory.SpringContextUtils;
import com.yt.plugin.quartz.service.QuartzService;
import com.yt.plugin.quartz.task.BaseTask;
import com.yt.plugin.quartz.utils.QzJsonUtils;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * 将继承BaseTask的计划任务自动初始化到quartz表中
 * @Author zhuzhengjie 2019年05月21日10:16:02
 */
@Component
@Order(10)
public class AutowireTask implements ApplicationRunner {
    Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private QuartzService quartzService;
    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //有状态的计划任务初始化
        Map<String, BaseTask> statefulMap =  SpringContextUtils.getBeansOfType(BaseTask.class);
        Iterator<Map.Entry<String, BaseTask>> it  = statefulMap.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, BaseTask> en =  it.next();
            BaseTask obj = (BaseTask)SpringContextUtils.getBean(en.getKey());
            checkAndInit(obj);
        }
        log.info("AutowireTask-run-map:{}", QzJsonUtils.toJson(statefulMap));
    }

    /**
     * 检测计划任务是否存在,不存在则新增计划任务
     * @param task
     */
    private void checkAndInit(BaseTask task){
        if(task == null)
            return ;
        PluginJobRes res = getPluginJobRes(task);
        boolean flag = quartzService.isExistJob(res.getJobName(), res.getJobGroupName());
        if (!flag) {//如果计划任务不存在则自动写入数据库
            JobDataMap dataMap = getJobDataMap(task);
            quartzService.addJob(res.getJobName(), res.getJobGroupName(), res.getClazz(),res.getJobCron(), dataMap);
        }
    }

    /***
     * 设置计划任务属性对象
     * @param task
     * @return
     */
    private PluginJobRes getPluginJobRes(BaseTask task){
        PluginJobRes res = new PluginJobRes();
        res.setClazz(task.getClass());
        res.setJobCron(systemConfig.getDefaultJobcron());
        res.setJobGroupName(systemConfig.getDefaultGroupName());
        res.setJobName(res.getJobGroupName()+"_"+task.getClass().getName());
        return  res;
    }

    /**
     * 获取计划任务自定义入参
     * @param task
     * @return
     */
    private JobDataMap getJobDataMap(BaseTask task){
        JobDataMap dataMap = new JobDataMap();
        if(task.getParamsList() != null && task.getParamsList().size()>0) {
            for(String param:task.getParamsList()){
                dataMap.put(param,null);
            }
        }
        return dataMap;
    }
}
