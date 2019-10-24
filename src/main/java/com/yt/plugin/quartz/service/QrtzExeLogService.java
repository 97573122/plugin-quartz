package com.yt.plugin.quartz.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yt.plugin.quartz.entity.QrtzExeLog;
import com.yt.plugin.quartz.entity.req.QueryLogReq;
import com.yt.plugin.quartz.mapper.QrtzExeLogMapper;
import com.yt.plugin.quartz.vo.QzPageModelResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(1)
public class QrtzExeLogService {

    @Autowired
    private QrtzExeLogMapper qrtzExeLogMapper;

    public void insert(QrtzExeLog qrtzExeLog){

        this.qrtzExeLogMapper.insert(qrtzExeLog);
    }


    public QzPageModelResult<QrtzExeLog> findByPage(QueryLogReq queryLogReq){
        QrtzExeLog exeLog = new QrtzExeLog();
        BeanUtils.copyProperties(queryLogReq,exeLog);
        PageHelper.startPage(queryLogReq.getCurrentPage(),queryLogReq.getPageSize());
        Page<QrtzExeLog> list = (Page<QrtzExeLog>) qrtzExeLogMapper.findByPage(exeLog);
        QzPageModelResult<QrtzExeLog> pageModel = new QzPageModelResult(list);
        return pageModel;
    }
}
