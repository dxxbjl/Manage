package io.github.yangyouwang.core.config;

import io.github.yangyouwang.core.job.TestQuartz;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangyouwang
 * @title: QuartzConfig
 * @projectName crud
 * @description: Quartz配置
 * @date 2021/4/12:32 PM
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail uploadTaskDetail() {
        return JobBuilder.newJob(TestQuartz.class).withIdentity("testQuartz").storeDurably().build();
    }

    @Bean
    public Trigger uploadTaskTrigger() {
        //设置执行频率
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(uploadTaskDetail())
                .withIdentity("testQuartz")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
