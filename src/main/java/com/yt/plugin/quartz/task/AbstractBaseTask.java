package com.yt.plugin.quartz.task;

import com.yt.plugin.quartz.service.QuartzLogService;
import com.yt.plugin.quartz.utils.QzExceptionUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractBaseTask implements BaseTask {
    protected Logger log = LoggerFactory.getLogger(getClass());
    public final List<String> paramsList = Collections.synchronizedList(new ArrayList<String>());

    @Autowired
    private QuartzLogService quartzLogService;

    public AbstractBaseTask() {
        setParamsList(paramsList);
    }

    public abstract void setParamsList(List<String> paramsMap);


    /**
     * 计划任务实现主体方法
     */
    public abstract void execute(JobDataMap jobDataMap);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        long bt = System.currentTimeMillis();
        String remark = "success";
        try {
            execute(context.getMergedJobDataMap());
        } catch (Exception e) {
            remark = QzExceptionUtils.getFullStackTrace(e);
            log.info("AbstractBaseTask-execute-exception:{}", remark);
        }
        long et = System.currentTimeMillis();
        //调用方法记录计划任务执行日志
        execLog(context, et - bt, remark);
    }

    /**
     * 记录计划任务执行日志
     *
     * @param context
     * @param execTime 执行时间
     * @param remark   执行结果
     * @author zhuzhengjie
     */
    private void execLog(JobExecutionContext context, Long execTime, String remark) {
        try {
            String jobName = context.getTrigger().getJobKey().getName();
            String jobGroup = context.getTrigger().getJobKey().getGroup();
            quartzLogService.log(jobName, jobGroup, execTime, remark);
        }catch (Exception e){
            log.info("AbstractBaseTask-execLog-exception:{}", QzExceptionUtils.getFullStackTrace(e));
        }
    }


    @Override
    public List<String> getParamsList() {
        return paramsList;
    }
}
