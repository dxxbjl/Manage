package io.github.yangyouwang.core.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.crud.system.mapper.SysTaskMapper;
import io.github.yangyouwang.crud.system.model.SysTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author yangyouwang
 * @title: SchedulingConfig
 * @projectName crud
 * @description: scheduling 配置
 * @date 2021/4/12:32 PM
 */
@Configuration
@EnableScheduling
public class SchedulingConfig implements SchedulingConfigurer {

    private Map<String, ScheduledFuture<?>> taskFutures = new ConcurrentHashMap<>();

    @Resource
    private SysTaskMapper sysTaskMapper;

    @Autowired
    private ApplicationContext applicationContext;

    private ScheduledTaskRegistrar scheduledTaskRegistrar;

    private static final int CORE_POOL_SIZE = 5;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        // 初始化
        this.init(scheduledTaskRegistrar);
        // 查询所有启用的任务
        List<SysTask> sysTasks = sysTaskMapper.selectList(new LambdaQueryWrapper<SysTask>()
                .eq(SysTask::getEnabled, Constants.ENABLED_YES));
        sysTasks.forEach(sysTask -> {
            this.addTriggerTask(sysTask.getName(),sysTask.getClassName(),sysTask.getMethodName(),sysTask.getCron());
        });
    }

    /**
     * 初始化
     */
    private void init(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //设置线程名称
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("quartz-pool-%d").build();
        //创建线程池
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(CORE_POOL_SIZE, namedThreadFactory);
        // 设置线程池
        scheduledTaskRegistrar.setScheduler(scheduledExecutorService);
        // 引用赋值
        this.scheduledTaskRegistrar = scheduledTaskRegistrar;
    }

    /**
     * 添加任务
     *
     * @param name 任务名称
     * @param className 类名称
     * @param methodName 方法
     * @param cron 表达式
     */
    public void addTriggerTask(String name, String className,String methodName,String cron) {
        TaskScheduler scheduler = scheduledTaskRegistrar.getScheduler();
        assert scheduler != null;
        ScheduledFuture<?> future = scheduler.schedule( () -> {
                    // 任务
                    try {
                        Object obj = applicationContext.getBean(className);
                        Method method = obj.getClass().getMethod(methodName);
                        method.invoke(obj);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                },
                triggerContext -> {
                    // 表达式
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                });
        taskFutures.put(name, future);
    }

    /**
     * 取消任务
     *
     * @param name 任务名称
     */
    public void cancelTriggerTask(String name)
    {
        if (taskFutures.containsKey(name)) {
            ScheduledFuture<?> future = taskFutures.get(name);
            future.cancel(true);
            taskFutures.remove(name);
        }
    }
}
