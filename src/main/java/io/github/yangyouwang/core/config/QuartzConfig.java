package io.github.yangyouwang.core.config;

import io.github.yangyouwang.common.constant.Constants;
import io.github.yangyouwang.crud.system.dao.SysTaskRepository;
import io.github.yangyouwang.crud.system.model.SysTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import javax.persistence.criteria.Predicate;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyouwang
 * @title: QuartzConfig
 * @projectName crud
 * @description: Quartz配置
 * @date 2021/4/12:32 PM
 */
@Configuration
@EnableScheduling
public class QuartzConfig implements SchedulingConfigurer {

    @Autowired
    private SysTaskRepository sysTaskRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        Specification<SysTask> query = (Specification<SysTask>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("enabled"), Constants.ENABLED_YES));
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        // 查询所有启用的任务
        List<SysTask> sysTasks = sysTaskRepository.findAll(query);
        sysTasks.forEach(sysTask -> {
            scheduledTaskRegistrar.addTriggerTask(
                    () -> {
                        // 任务
                        try {
                            Object obj = applicationContext.getBean(sysTask.getClassName());
                            Method method = obj.getClass().getMethod(sysTask.getMethodName(),null);
                            method.invoke(obj);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    },
                    triggerContext -> {
                        // 表达式
                        return new CronTrigger(sysTask.getCron()).nextExecutionTime(triggerContext);
                    }
            );
        });
    }
}
