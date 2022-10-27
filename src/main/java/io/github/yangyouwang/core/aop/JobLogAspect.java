package io.github.yangyouwang.core.aop;

import io.github.yangyouwang.common.constant.ConfigConsts;
import io.github.yangyouwang.crud.qrtz.entity.JobLog;
import io.github.yangyouwang.crud.qrtz.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;


/**
 * Description: 工作调度任务拦截 (日志记录) <br/>
 * date: 2022/10/26 20:41<br/>
 *
 * @author yangyouwang<br />
 * @version v1.0
 * @since JDK 1.8
 */
@Slf4j
@Aspect
@Component
public class JobLogAspect {

    @Resource
    private JobLogService jobLogService;

    /**
     * 只对于execute切入
     */
    @Pointcut("execution(public void io.github.yangyouwang.core.job.*.execute(..))")
    public void jobLogPointCut() {
    }

    /**
     * 切点通知(方法执行后)
     */
    @AfterReturning("jobLogPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        JobLog jobLog = handleJobLog(joinPoint);
        jobLog.setStatus(ConfigConsts.SUCCESS_STATUS);
        jobLogService.save(jobLog);
    }

    /**
     * 抛出异常后的通知
     */
    @AfterThrowing(value = "jobLogPointCut()", throwing = "e")
    private void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        JobLog jobLog = handleJobLog(joinPoint);
        String errMsg = e.getMessage();
        jobLog.setExceptionInfo(errMsg);
        jobLog.setStatus(ConfigConsts.ERROR_STATUS);
        jobLogService.save(jobLog);
    }

    private JobLog handleJobLog(JoinPoint joinPoint) {
        JobLog jobLog = new JobLog();
        String taskName = Thread.currentThread().getName();
        jobLog.setTaskName(taskName);
        String taskGroup = Thread.currentThread().getThreadGroup().getName();
        jobLog.setTaskGroup(taskGroup);
        String methodName = joinPoint.getSignature().getName();
        jobLog.setInvokeTarget(methodName);
        Object[] argsValue = Arrays.stream(joinPoint.getArgs()).toArray(Object[]::new);
        jobLog.setInvokeParam(StringUtils.join(argsValue));
        return jobLog;
    }
}
