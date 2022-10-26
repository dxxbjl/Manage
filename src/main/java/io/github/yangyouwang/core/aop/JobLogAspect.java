package io.github.yangyouwang.core.aop;

import io.github.yangyouwang.crud.qrtz.entity.JobLog;
import io.github.yangyouwang.crud.qrtz.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


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
    public void doAfterReturning() {
        log.info("开始写入日志");
    }

    /**
     * 抛出异常后的通知
     */
    @AfterThrowing(value = "jobLogPointCut()", throwing = "e")
    private void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        log.info("开始写入日志 = 出错了");
    }

    private JobLog handleJobLog(JoinPoint joinPoint) {
        JobLog jobLog = new JobLog();
        // TODO: 2022/10/27 保存定时日志功能待实现 
        return jobLog;
    }
}
