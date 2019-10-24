package com.yt.plugin.quartz.service;

import com.yt.plugin.quartz.entity.res.AllJobsRes;
import com.yt.plugin.quartz.exception.QuartzException;
import com.yt.plugin.quartz.utils.QzExceptionUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


/**
 * quartz操作实现类
 * @Author zhuzhengjie
 */
@Service(value = "quartzService")
public class QuartzService {
    // 日志处理
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private Scheduler scheduler;


    /**
     * 添加一个job
     *
     * @param name
     * @param group
     * @param jobclazz
     * @param cron 计划任务执行时间cron表达式
     * @return boolean 返回类型
     * @throws QuartzException
     */
    public boolean addJob(String name, String group,
                          Class<? extends Job> jobclazz, String cron, JobDataMap newJobDataMap) throws QuartzException {
        boolean flag = false;
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobclazz).setJobData(newJobDataMap)
                    .withIdentity(name, group).build(); // 1、创建一个JobDetail实例，指定Quartz
            // 创建任务触发器
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(name, group)
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(cron)).build();
            scheduler.scheduleJob(jobDetail, trigger);
            flag = true;
            logger.info("创建定时任务=> [任务名称：{} 任务组：{}] 成功，定时执行时间：[{}]", name, group, cron);
        } catch (SchedulerException e) {
            flag = false;
            logger.info("创建定时任务=> [任务名称：{} 任务组：{}] 失败，定时执行时间：[{}]", name, group, cron, QzExceptionUtils.getFullStackTrace(e));
            throw new QuartzException("notice.addJob");
        }
        return flag;
    }

    /**
     * 修改job执行时间
     *
     * @param name
     * @param group
     * @param cron
     * @return boolean 返回类型
     * @throws QuartzException
     */
    public boolean modifyJobTime(String name, String group, String cron) throws QuartzException {
        boolean flag = false;
        try {
            TriggerKey tk = TriggerKey.triggerKey(name, group);
            // 构造任务触发器
            Trigger trg = TriggerBuilder
                    .newTrigger()
                    .withIdentity(name, group)
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(cron)).build();

            scheduler.rescheduleJob(tk, trg);
            flag = true;
            logger.info("修改定时任务=> [任务名称：{} 任务组：{}] 成功，修改执行时间：[{}]", name, group, cron);
        } catch (SchedulerException e) {
            flag = false;
            logger.info("修改定时任务=> [任务名称：{} 任务组：{}] 失败，修改执行时间：[{}]", name, group, cron, e);
            throw new QuartzException("notice.modifyJobTime");
        }
        return flag;
    }

    /**
     * 暂停任务
     *
     * @param name
     * @param group
     * @throws QuartzException
     */
    public boolean parseJob(String name, String group) throws QuartzException {
        boolean flag = false;
        try {
            JobKey jobKey = JobKey.jobKey(name, group);
            scheduler.pauseJob(jobKey);
            flag = true;
            logger.info("暂停定时任务=> [任务名称：{} 任务组：{}] 成功，暂停执行时间：[{}]", new Date());
        } catch (SchedulerException e) {
            flag = false;
            logger.info("暂停定时任务=> [任务名称：{} 任务组：{}] 失败，暂停执行时间：[{}]", name, group, new Date(), e);
            throw new QuartzException("notice.parseJob");
        }
        return flag;
    }

    /**
     * 删除任务
     *
     * @param name
     * @param group
     * @throws QuartzException
     */
    public boolean deleteJob(String name, String group) throws QuartzException {
        boolean flag = false;
        try {
            JobKey jobKey = JobKey.jobKey(name, group);
            scheduler.deleteJob(jobKey);
            flag = true;
            logger.info("删除定时任务=> [任务名称：{} 任务组：{}] 成功，暂停执行时间：[{}]", new Date());
        } catch (SchedulerException e) {
            flag = false;
            logger.info("删除定时任务=> [任务名称：{} 任务组：{}] 失败，暂停执行时间：[{}]", name, group, new Date(), QzExceptionUtils.getFullStackTrace(e));
            throw new QuartzException("notice.deleteJob");
        }
        return flag;
    }

    /**
     * 恢复任务执行
     *
     * @param name
     * @param group
     * @throws QuartzException
     */
    public boolean resumeJob(String name, String group) throws QuartzException {
        boolean flag = false;
        try {
            JobKey jobKey = JobKey.jobKey(name, group);
            scheduler.resumeJob(jobKey);
            flag = true;
            logger.info("恢复定时任务=> [任务名称：{} 任务组：{}] 成功，恢复执行时间：[{}]", name, group, new Date());
        } catch (SchedulerException e) {
            flag = false;
            logger.info("恢复定时任务=> [任务名称：{} 任务组：{}] 失败，恢复执行时间：[{}]", name, group, new Date(), QzExceptionUtils.getFullStackTrace(e));
            throw new QuartzException("notice.resumeJob");
        }
        return flag;
    }

    /**
     * 检测任务是否存在
     *
     * @param name
     * @param group
     * @throws QuartzException
     */
    public boolean isExistJob(String name, String group) throws QuartzException {
        boolean flag = false;
        try {
            JobKey jobKey = JobKey.jobKey(name, group);
            flag = scheduler.checkExists(jobKey);
        } catch (SchedulerException e) {
            logger.info("检测任务=> [任务名称：{} 任务组：{}] 失败，检测执行时间：[{}]", name, group, new Date(), QzExceptionUtils.getFullStackTrace(e));
            throw new QuartzException("notice.isExistJob");
        }
        return flag;
    }

    /**
     * 立即执行任务
     *
     * @param name
     * @param group
     * @throws QuartzException
     */
    public boolean exeJob(String name, String group, JobDataMap jobDataMap) throws QuartzException{
        boolean flag = false;
        try {
            JobKey jobKey = JobKey.jobKey(name, group);
            scheduler.triggerJob(jobKey,jobDataMap);
        } catch (Exception e) {
            logger.info("检测任务=> [任务名称：{} 任务组：{}] 失败，检测执行时间：[{}]", name, group, new Date(), QzExceptionUtils.getFullStackTrace(e));
            throw new QuartzException("notice.exeJob");
        }
        return flag;
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     */
    public List<AllJobsRes> queryAllJob() {
        List<AllJobsRes> jobList = new ArrayList<AllJobsRes>();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                AllJobsRes res = new AllJobsRes();
                res.setJobName(jobKey.getName());
                res.setJobGroupName(jobKey.getGroup());
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    //备注设置计划任务类名,便于识别,后续继续优化
                    String triggerKey = trigger.getKey().toString();
                    if(StringUtils.hasLength(triggerKey)){
                        triggerKey = triggerKey.substring(triggerKey.lastIndexOf(".")+1,triggerKey.length());
                        res.setDescription(triggerKey);
                    }
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    res.setJobStatus(triggerState.name());

                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        res.setJobCron(cronExpression);
                    }
                }
                jobList.add(res);
            }
        } catch (SchedulerException e) {
            logger.error("queryAllJob-exception:{}", QzExceptionUtils.getFullStackTrace(e));
        }
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     *
     * @return
     */
    public List<AllJobsRes> queryRunJob() {
        List<AllJobsRes> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<AllJobsRes>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                AllJobsRes res = new AllJobsRes();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();

                res.setJobName(jobKey.getName());
                res.setJobGroupName(jobKey.getGroup());
                res.setDescription("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                res.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    res.setJobCron(cronExpression);
                }
                jobList.add(res);
            }
        } catch (SchedulerException e) {
            logger.error("queryRunJob-exception:{}", QzExceptionUtils.getFullStackTrace(e));
        }
        return jobList;
    }

}
