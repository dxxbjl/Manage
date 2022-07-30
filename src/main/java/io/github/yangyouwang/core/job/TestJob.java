package io.github.yangyouwang.core.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * @author yangyouwang
 * @title: TestJob
 * @projectName crud
 * @description: 测试job
 * @date 2021/4/9下午9:25
 */
@DisallowConcurrentExecution
public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("测试");
    }
}
