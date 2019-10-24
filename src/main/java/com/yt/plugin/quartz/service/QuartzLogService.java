package com.yt.plugin.quartz.service;

import com.yt.plugin.quartz.entity.QrtzExeLog;
import com.yt.plugin.quartz.factory.SpringContextUtils;
import com.yt.plugin.quartz.utils.QzQzDateUtils;
import com.yt.plugin.quartz.utils.QzIpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
public class QuartzLogService {
    @Autowired
    private QrtzExeLogService qrtzExeLogService;


    public void log(String jobName,String jobGroup,Long takeTimes, String remark) {
        QrtzExeLog exeLog = new QrtzExeLog();
        exeLog.setJobName(jobName);
        exeLog.setJobGroup(jobGroup);
        exeLog.setExeIp(QzIpUtils.getLocalIP());
        exeLog.setExeTime(QzQzDateUtils.now());
        exeLog.setTakeTimes(takeTimes.intValue());
        exeLog.setRemark(remark);
        qrtzExeLogService.insert(exeLog);
    }
}
