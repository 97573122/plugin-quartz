package com.yt.plugin.quartz.mapper;


import com.yt.plugin.quartz.entity.QrtzExeLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QrtzExeLogMapper {
    int deleteByPrimaryKey(QrtzExeLog key);

    int insert(QrtzExeLog record);

    int insertSelective(QrtzExeLog record);

    QrtzExeLog selectByPrimaryKey(QrtzExeLog key);

    List<QrtzExeLog> selectObjectListByWhere(QrtzExeLog record);

    List<QrtzExeLog> findByPage(QrtzExeLog record);

    int selectCountByWhere(QrtzExeLog record);

    int updateByPrimaryKeySelective(QrtzExeLog record);

    int updateByPrimaryKey(QrtzExeLog record);
}