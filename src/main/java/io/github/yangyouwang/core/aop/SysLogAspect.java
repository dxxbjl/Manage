package io.github.yangyouwang.core.aop;

import io.github.yangyouwang.common.annotation.CrudLog;
import io.github.yangyouwang.crud.system.dao.SysLogRepository;
import io.github.yangyouwang.crud.system.model.SysLog;
import org.apache.tomcat.util.buf.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;

/**
 * @author yangyouwang
 * @title: SysLogAspect
 * @projectName crud
 * @description: 日志切面
 * @date 2021/4/18:37 AM
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogRepository sysLogRepository;

    /**
     * 切入点
     */
    @Pointcut(value = "@annotation(io.github.yangyouwang.common.annotation.CrudLog)")
    private void logPointCut() {

    }

    /**
     * 抛出异常后的通知
     */
    @AfterThrowing(value = "logPointCut() && @annotation(crudLog)", throwing = "e")
    private void logAfterThrowing(JoinPoint joinPoint, CrudLog crudLog, Exception e) {
        String className = joinPoint.getTarget().getClass().getSimpleName(); // 类名称
        String methodName = joinPoint.getSignature().getName(); //方法名称
        String packageName = joinPoint.getSignature().getDeclaringTypeName(); // 包名称

        String[] argsValue = Arrays.stream(joinPoint.getArgs()).toArray(String[]::new); // 参数值
        String[] argsNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames(); // 参数名

        StringWriter stackTraceWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTraceWriter, true));  //异常堆栈信息
        String errMsg = e.getMessage(); //错误信息
        String exceptionName = e.toString();//异常类型

        SysLog sysLog = new SysLog();
        sysLog.setPackageName(packageName);
        sysLog.setClassName(className);
        sysLog.setMethodName(methodName);
        sysLog.setArgsName(StringUtils.join(argsNames));
        sysLog.setArgsValue(StringUtils.join(argsValue));
        sysLog.setStackTrace(stackTraceWriter.toString());
        sysLog.setErrMsg(errMsg);
        sysLog.setExceptionName(exceptionName);
        sysLog.setCreateTime(new Date());
        sysLogRepository.save(sysLog);
    }
}
