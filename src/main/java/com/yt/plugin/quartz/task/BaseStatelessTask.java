package com.yt.plugin.quartz.task;

import com.yt.plugin.quartz.factory.SpringContextUtils;
import com.yt.plugin.quartz.service.QuartzLogService;
import com.yt.plugin.quartz.utils.QzExceptionUtils;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 标记无状态job基类,会同时执行多个job实例
 *
 * @Author zhuzhengjie
 * @DisallowConcurrentExecution 添加到 Job 类后，Quartz 将不会同时执行多个 Job 实例
 * @PersistJobDataAfterExecution 添加到 Job 类后，表示 Quartz 将会在成功执行 execute() 方法后（没有抛出异常）更新 JobDetail 的 JobDataMap，
 * 下一次执行相同的任务（JobDetail）将会得到更新后的值，而不是原始的值。就像@DisallowConcurrentExecution 一样，这个注释基于 JobDetail 而不是 Job 类的实例。
 * <p>
 * 如果你使用了 @PersistJobDataAfterExecution 注释，那么强烈建议你使用 @DisallowConcurrentExecution 注释，这是为了避免出现并发问题，
 * 当多个 Job 实例同时执行的时候，到底使用了哪个数据将变得很混乱。
 */
@Order(2)
public abstract class BaseStatelessTask extends AbstractBaseTask {

    public BaseStatelessTask() {
        super();
    }




}
