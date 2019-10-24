package com.yt.plugin.quartz.controller;

import com.yt.plugin.quartz.entity.QrtzExeLog;
import com.yt.plugin.quartz.entity.req.AddJobReq;
import com.yt.plugin.quartz.entity.req.ExeJobReq;
import com.yt.plugin.quartz.entity.req.ModifyJobReq;
import com.yt.plugin.quartz.entity.req.QueryLogReq;
import com.yt.plugin.quartz.entity.res.AllJobsRes;
import com.yt.plugin.quartz.service.QrtzExeLogService;
import com.yt.plugin.quartz.service.QuartzService;
import com.yt.plugin.quartz.task.BaseTask;
import com.yt.plugin.quartz.task.TestJob;
import com.yt.plugin.quartz.utils.QzExceptionUtils;
import com.yt.plugin.quartz.utils.QzJsonUtils;
import com.yt.plugin.quartz.vo.QzPageModelResult;
import com.yt.plugin.quartz.vo.QzModelResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobDataMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/plugin/quartz")
@Api(value = "/plugin/quartz", tags = "计划任务")
public class QuartzController {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private QuartzService quartzService;
    @Autowired
    private QrtzExeLogService qrtzExeLogService;


    @ApiOperation(value = "plugin-quartz:查询计划任务的执行日志")
    @PostMapping("/queryExeLogByJobName")
    @ResponseBody
    public QzPageModelResult<QrtzExeLog> queryExeLogByJobName(@RequestBody QueryLogReq queryLogReq){
        QzPageModelResult<QrtzExeLog> logList = this.qrtzExeLogService.findByPage(queryLogReq);
        return logList;
    }

    @ApiOperation(value = "plugin-quartz:新增计划任务")
    @PostMapping("/add")
    @ResponseBody
    public QzModelResult<Boolean> add(@RequestBody AddJobReq req) {
        QzModelResult<Boolean> resp = new QzModelResult(false);
        try {
            Class<? extends BaseTask> clz = (Class<? extends BaseTask>) Class.forName(req.getJobClazz());
            JobDataMap newJobDataMap = new JobDataMap();
            boolean flag = quartzService.addJob(req.getName(),req.getGroup(),TestJob.class,req.getCron(),newJobDataMap);
            resp.setModel(flag);
        } catch (ClassNotFoundException e) {
            log.error("QuartzController-add-exception:{}", QzExceptionUtils.getFullStackTrace(e));
        }
        return resp;
    }

    @ApiOperation(value = "plugin-quartz:修改计划任务cron表达式")
    @PostMapping("/edit")
    @ResponseBody
    public QzModelResult<Boolean> edit(@RequestBody AddJobReq req) {
        QzModelResult<Boolean> resp = new QzModelResult(false);
        try {
            JobDataMap newJobDataMap = new JobDataMap();
            boolean flag = quartzService.modifyJobTime(req.getName(), req.getGroup(), req.getCron());
            resp.setModel(flag);
        }catch (Exception e){
            resp.setErrorMsgWithDefaultCode("任务表达式错误,请检查并修改后再操作!");
        }
        return resp;
    }

    @ApiOperation(value = "plugin-quartz:暂停计划任务")
    @PostMapping("/parseJob")
    @ResponseBody
    public QzModelResult<Boolean> parseJob(@RequestBody ModifyJobReq req) {
        QzModelResult<Boolean> resp = new QzModelResult(false);
        JobDataMap newJobDataMap = new JobDataMap();
        boolean flag = quartzService.parseJob(req.getName(),req.getGroup());
        resp.setModel(flag);
        return resp;
    }


    @ApiOperation(value = "plugin-quartz:启用计划任务")
    @PostMapping("/resumeJob")
    @ResponseBody
    public QzModelResult<Boolean> resumeJob(@RequestBody ModifyJobReq req) {
        QzModelResult<Boolean> resp = new QzModelResult(false);
        JobDataMap newJobDataMap = new JobDataMap();
        boolean flag = quartzService.resumeJob(req.getName(),req.getGroup());
        resp.setModel(flag);
        return resp;
    }

    @ApiOperation(value = "plugin-quartz:删除计划任务")
    @PostMapping("/deleteJob")
    @ResponseBody
    public QzModelResult<Boolean> deleteJob(@RequestBody ModifyJobReq req) {
        QzModelResult<Boolean> resp = new QzModelResult(false);
        JobDataMap newJobDataMap = new JobDataMap();
        boolean flag = quartzService.deleteJob(req.getName(),req.getGroup());
        resp.setModel(flag);
        return resp;
    }

    @ApiOperation(value = "plugin-quartz:执行计划任务")
    @PostMapping("/exeJob")
    @ResponseBody
    public QzModelResult<Boolean> exeJob(@RequestBody String jsonStr){
        QzModelResult<Boolean> resp = new QzModelResult(false);
        if(StringUtils.isEmpty(jsonStr)){
            log.warn("exeJob-params must not be null.");
            return resp;
        }
        Map<String,Object> map = QzJsonUtils.toMap(jsonStr);
        String name = "name",group="group";
        //校验
        if(map.get(name) == null||map.get(group) == null){
            log.warn("exeJob-params name and group must not be null.");
            return resp;
        }
        ExeJobReq req = new ExeJobReq();
        req.setName(map.get(name).toString());
        req.setGroup(map.get(group).toString());
        JobDataMap jobDataMap = new JobDataMap();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,Object> en = (Map.Entry<String, Object>) it.next();
            jobDataMap.put(en.getKey(),en.getValue());
        }
        boolean flag = quartzService.exeJob(req.getName(),req.getGroup(),jobDataMap);
        resp.setModel(flag);
        return resp;
    }

    @ApiOperation(value = "plugin-quartz:查询系统所有计划任务")
    @PostMapping("/queryAllJobs")
    @ResponseBody
    public QzModelResult<List<AllJobsRes>> queryAllJobs(){
        QzModelResult<List<AllJobsRes>> resp = new QzModelResult(false);
        List<AllJobsRes> jobs = this.quartzService.queryAllJob();
        resp.setModel(jobs);
        return resp;
    }
}

