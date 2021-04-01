package io.github.yangyouwang.core.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.UUID;

/**
 * @author yangyouwang
 * @title: TestQuartz
 * @projectName crud
 * @description: 定时任务
 * @date 2021/4/12:35 PM
 */
@DisallowConcurrentExecution
public class TestQuartz extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String random= UUID.randomUUID().toString();
        System.out.println("任务开始:"+random);
    }
}